package com.app.linkedin.posts_comment_service.services.impl;

import com.app.linkedin.posts_comment_service.auth.UserContextHolder;
import com.app.linkedin.posts_comment_service.clients.PostsServiceClient;
import com.app.linkedin.posts_comment_service.dtos.CommentRequestDto;
import com.app.linkedin.posts_comment_service.dtos.CommentResponseDto;
import com.app.linkedin.posts_comment_service.dtos.PostsDto;
import com.app.linkedin.posts_comment_service.entities.PostsComment;
import com.app.linkedin.posts_comment_service.events.PostsCommentedEvent;
import com.app.linkedin.posts_comment_service.events.PostsUncommentedEvent;
import com.app.linkedin.posts_comment_service.exceptions.BadRequestException;
import com.app.linkedin.posts_comment_service.exceptions.ResourceNotFoundException;
import com.app.linkedin.posts_comment_service.repositories.PostsCommentRepository;
import com.app.linkedin.posts_comment_service.services.CommentsService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentsService {

    private final PostsCommentRepository postsCommentRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;
    private final PostsServiceClient postsServiceClient;
    private final KafkaTemplate<Long,PostsCommentedEvent> kafkaTemplate;
    private final KafkaTemplate<Long, PostsUncommentedEvent> postsUncommentedKafkaTemplate;


    @Override
    public void addComment(CommentRequestDto commentRequestDto, Long postId) {
        Long userId = UserContextHolder.getCurrentUserId();
        PostsDto postsDto = getPost(postId);
        if (checkProfanity(commentRequestDto.getComment()))
            throw new BadRequestException("You cannot use vulgar words in the comments!");

        PostsComment postsComment = PostsComment.builder().postId(postId)
                                        .comment(commentRequestDto.getComment())
                                        .userId(userId)
                                        .build();
        postsCommentRepository.save(postsComment);
        log.info("Comment successfully done!");

        PostsCommentedEvent postsCommentedEvent = PostsCommentedEvent
                .builder().commentedById(userId)
                .postId(postId)
                .creatorId(postsDto.getUserId()).build();
        kafkaTemplate.send("posts-commented-topic",postId,postsCommentedEvent);
    }

    @Override
    public void deleteComment(Long commentId) {
        PostsComment postsComment = postsCommentRepository.findById(commentId).orElseThrow(()->
                                    new ResourceNotFoundException("Comment doesn't exists!"));
        Long userId = UserContextHolder.getCurrentUserId();

       if(!existsByPostId(postsComment.getPostId()))
           throw new ResourceNotFoundException("Cannot delete comment as posts associated with the comment is either deleted or doesn't exists!");

        if(!userId.equals(postsComment.getUserId()))
            throw new BadRequestException("Cannot delete comment as you are not the owner of this comment!");

        postsCommentRepository.deleteById(commentId);

        PostsUncommentedEvent postsUncommentedEvent = PostsUncommentedEvent
                .builder().postId(postsComment.getPostId())
                .userId(userId)
                .build();
        postsUncommentedKafkaTemplate.send("posts-uncommented-topic",postsComment.getPostId(),postsUncommentedEvent);
    }

    @Override
    public List<CommentResponseDto> getAllCommentsOfPosts(Long postId, PageRequest pageRequest) {
        if (!existsByPostId(postId))
            throw new BadRequestException("Post with given id doesn't exists!");

        List<PostsComment> postsComments = postsCommentRepository.findAllByPostId(postId,pageRequest).getContent();
        return postsComments.stream().map(postsComment -> modelMapper.map(postsComment,CommentResponseDto.class))
                .collect(Collectors.toList());
    }

    Boolean checkProfanity(String comment)
    {
        String response;
        String url = "http://www.purgomalum.com/service/containsprofanity?text=" +
                URLEncoder.encode(comment);

        try {
            response = restTemplate.getForObject(url, String.class);
            if (response!=null && response.equals("true"))
                return true;
        }catch (Exception e)
        {
            log.info("Caught Exception while making Profanity check call, Error : {}",e.getLocalizedMessage());
        }
        return false;
    }

    PostsDto getPost(Long postId)
    {
        try {
            PostsDto postsDto = postsServiceClient.getPost(postId);
            return postsDto;
        }catch(FeignException e)
        {
            log.info("Posts doesn't exists with id {} !",postId);
            throw new BadRequestException("Post with id "+postId+" doesn't exists!");
        }
    }

    Boolean existsByPostId(Long postId)
    {
        return postsServiceClient.existsByPostId(postId);
    }
}

package com.app.linkedin.post_service.services.impl;

import com.app.linkedin.post_service.auth.UserContextHolder;
import com.app.linkedin.post_service.dtos.CommentRequestDto;
import com.app.linkedin.post_service.dtos.CommentResponseDto;
import com.app.linkedin.post_service.entities.PostsComment;
import com.app.linkedin.post_service.exceptions.BadRequestException;
import com.app.linkedin.post_service.exceptions.ResourceNotFoundException;
import com.app.linkedin.post_service.repositories.PostsCommentRepository;
import com.app.linkedin.post_service.services.CommentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
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
    private String response;

    @Override
    public void addComment(CommentRequestDto commentRequestDto,Long postId) {
        Long userId = UserContextHolder.getCurrentUserId();
        String url = "http://www.purgomalum.com/service/containsprofanity?text=" +
                URLEncoder.encode(commentRequestDto.getComment(), StandardCharsets.UTF_8);

        try {
            response = restTemplate.getForObject(url, String.class);
        }catch (Exception e)
        {
            log.info("Caught Exception while making Profanity check call, Error : {}",e.getLocalizedMessage());
        }

        if (response!=null && response.equals("true"))
            throw new BadRequestException("You cannot use vulgar words in the comments!");

        PostsComment postsComment = PostsComment.builder().postId(postId)
                                        .comment(commentRequestDto.getComment())
                                        .userId(userId)
                                        .build();
        postsCommentRepository.save(postsComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        PostsComment postsComment = postsCommentRepository.findById(commentId).orElseThrow(()->
                                    new ResourceNotFoundException("Comment doesn't exists!"));
        Long userId = UserContextHolder.getCurrentUserId();

        if(!userId.equals(postsComment.getUserId()))
            throw new BadRequestException("Cannot delete comment as you are not the owner of this comment!");

        postsCommentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentResponseDto> getAllCommentsOfPosts(Long postId, PageRequest pageRequest) {
        List<PostsComment> postsComments = postsCommentRepository.findAllByPostId(postId,pageRequest).getContent();
        return postsComments.stream().map(postsComment -> modelMapper.map(postsComment,CommentResponseDto.class))
                .collect(Collectors.toList());
    }

}

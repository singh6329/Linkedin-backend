package com.app.linkedin.post_service.services.impl;

import com.app.linkedin.post_service.auth.UserContextHolder;
import com.app.linkedin.post_service.clients.ConnectionClient;
import com.app.linkedin.post_service.dtos.PostRequestDto;
import com.app.linkedin.post_service.dtos.PostsDto;
import com.app.linkedin.post_service.entities.Posts;
import com.app.linkedin.post_service.events.PostCreatedEvent;
import com.app.linkedin.post_service.exceptions.BadRequestException;
import com.app.linkedin.post_service.exceptions.ResourceNotFoundException;
import com.app.linkedin.post_service.repositories.PostsRepository;
import com.app.linkedin.post_service.services.PostsService;
import com.app.linkedin.posts_like_service.events.PostLikedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsServiceImpl implements PostsService {
    private final ModelMapper modelMapper;
    private final PostsRepository postsRepository;
    private final KafkaTemplate<Long,PostCreatedEvent> kafkaTemplate;

    @Override
    public PostsDto createPost(PostRequestDto postRequestDto) {
        Posts posts = modelMapper.map(postRequestDto,Posts.class);
        Long userId = UserContextHolder.getCurrentUserId();
        posts.setUserId(userId);
        posts.setLikesCount(0L);
        posts.setCommentsCount(0L);
        Posts savedPost = postsRepository.save(posts);
        PostCreatedEvent postCreatedEvent = PostCreatedEvent.builder().creatorId(userId)
                .postId(savedPost.getId()).content(savedPost.getContent()).build();
        kafkaTemplate.send("post-created-topic",postCreatedEvent);

        return modelMapper.map(savedPost,PostsDto.class);
    }

    @Override
    public PostsDto getPostById(Long postId) {
        Posts post = postsRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post doesn't exists with id: "+postId));

        return modelMapper.map(post,PostsDto.class);
    }

    @Override
    public List<PostsDto> getAllPostsOfUser(Long userId) {
        List<Posts> posts = postsRepository.findAllByUserId(userId);
        return posts.stream()
                            .map(post->modelMapper.map(post,PostsDto.class))
                            .collect(Collectors.toList());
    }

    @Override
    public void deletePost(Long postId) {
        Posts post = postsRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post with id "+postId+" doesn't exists!"));

        Long userId = UserContextHolder.getCurrentUserId();
        if(!userId.equals(post.getUserId()))
            throw new BadRequestException("Cannot delete post as you are not the owner of the post!");
        postsRepository.deleteById(postId);

    }

    @Override
    public void increaseLikesCounterOfPosts(Long postId) {
        Posts posts = postsRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Posts with id "+postId+" doesn't exists!"));

        posts.setLikesCount(posts.getLikesCount()+1);
        postsRepository.save(posts);
    }

    @Override
    public void decreaseLikesCounterOfPost(Long postId) {
        Posts posts = postsRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Posts with id "+postId+" doesn't exists!"));

        posts.setLikesCount(posts.getLikesCount()-1);
        postsRepository.save(posts);
    }

    @Override
    public void increaseCommentsCounterOfPosts(Long postId) {
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Posts with id "+postId+" doesn't exists!"));

        posts.setCommentsCount(posts.getCommentsCount()+1);
        postsRepository.save(posts);
    }

    @Override
    public void decreaseCommentsCounterOfPosts(Long postId) {
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Posts with id "+postId+" doesn't exists!"));

        posts.setCommentsCount(posts.getCommentsCount()-1);
        postsRepository.save(posts);
    }

    @Override
    public Boolean existsByPostId(Long postId) {
        return postsRepository.existsById(postId);
    }

}

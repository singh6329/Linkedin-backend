package com.app.linkedin.posts_like_service.services.impl;

import com.app.linkedin.posts_like_service.auth.UserContextHolder;
import com.app.linkedin.posts_like_service.clients.PostsFeignClient;
import com.app.linkedin.posts_like_service.dtos.PostsDto;
import com.app.linkedin.posts_like_service.entities.PostsLike;
import com.app.linkedin.posts_like_service.events.PostLikedEvent;
import com.app.linkedin.posts_like_service.events.PostUnlikedEvent;
import com.app.linkedin.posts_like_service.exceptions.BadRequestException;
import com.app.linkedin.posts_like_service.exceptions.ResourceNotFoundException;
import com.app.linkedin.posts_like_service.repositories.PostsLikeRepository;
import com.app.linkedin.posts_like_service.services.LikesService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikesServiceImpl implements LikesService {

    private final PostsFeignClient postsFeignClient;
    private final PostsLikeRepository postsLikeRepository;
    private final KafkaTemplate<Long, PostLikedEvent> kafkaTemplate;
    private final KafkaTemplate<Long, PostUnlikedEvent> postUnlikedEventKafkaTemplate;

    @Override
    public void likePost(Long postId) {
        Long userId = UserContextHolder.getCurrentUserId();
        PostsDto post = getPost(postId);
        log.info("Attempting to like the post with post-id: {}",postId);

        Boolean likeAlreadyExists = postsLikeRepository.existsByUserIdAndPostId(userId,postId);
        if (likeAlreadyExists)
            throw new BadRequestException("Post has already been liked!");

        PostsLike postsLike = PostsLike.builder()
                                                 .postId(postId)
                                                .userId(userId)
                                                .build();
        postsLikeRepository.save(postsLike);
        log.info("Post liked successfully!");
        PostLikedEvent postLikedEvent = PostLikedEvent.builder()
                                                    .likedByUserId(userId)
                                                    .postId(postId)
                                                    .creatorId(post.getUserId())
                                                    .build();
        kafkaTemplate.send("post-liked-topic",postId,postLikedEvent);
    }

    @Override
    @Transactional
    public void unlikePost(Long postId) {
    Long userId = UserContextHolder.getCurrentUserId();

    if(!existsByPostId(postId))
        throw new ResourceNotFoundException("Post with id "+postId+" doesn't exists!");

    Boolean likeExists = postsLikeRepository.existsByUserIdAndPostId(userId,postId);
    if(!likeExists)
        throw new BadRequestException("Cannot unlike the post as User hasn't liked the post!");

    postsLikeRepository.deleteByUserIdAndPostId(userId,postId);
    PostUnlikedEvent postUnlikedEvent = PostUnlikedEvent.builder()
            .postId(postId)
            .userId(userId)
            .build();
    postUnlikedEventKafkaTemplate.send("post-unliked-topic",postId,postUnlikedEvent);
    }

    PostsDto getPost(Long postId)
    {
        try {
            return postsFeignClient.getPost(postId);
        }catch (FeignException feignException)
        {
            throw new ResourceNotFoundException("Post with id "+postId+" doesn't exists!");
        }
    }

    Boolean existsByPostId(Long postId)
    {
        return postsFeignClient.existsByPostId(postId);
    }
}

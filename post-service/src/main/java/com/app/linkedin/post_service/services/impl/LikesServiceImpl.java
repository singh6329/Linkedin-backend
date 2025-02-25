package com.app.linkedin.post_service.services.impl;

import com.app.linkedin.post_service.auth.UserContextHolder;
import com.app.linkedin.post_service.dtos.PostsDto;
import com.app.linkedin.post_service.entities.Posts;
import com.app.linkedin.post_service.entities.PostsLike;
import com.app.linkedin.post_service.events.PostLikedEvent;
import com.app.linkedin.post_service.exceptions.BadRequestException;
import com.app.linkedin.post_service.exceptions.ResourceNotFoundException;
import com.app.linkedin.post_service.repositories.PostsLikeRepository;
import com.app.linkedin.post_service.repositories.PostsRepository;
import com.app.linkedin.post_service.services.LikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikesServiceImpl implements LikesService {

    private final PostsRepository postsRepository;
    private final PostsLikeRepository postsLikeRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long,PostLikedEvent> kafkaTemplate;

    @Override
    public void likePost(Long postId) {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Attempting to like the post with id: {}",postId);
        Posts posts = postsRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post not found with id: "+postId));

        Boolean alreadyLiked = postsLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(alreadyLiked)
            throw new BadRequestException("Post already liked!");

        PostsLike postsLike = PostsLike.builder().postId(postId)
                                .userId(userId).build();
        postsLikeRepository.save(postsLike);
        PostLikedEvent postLikedEvent = PostLikedEvent.builder()
                        .postId(postId)
                                .likedByUserId(userId)
                                        .creatorId(posts.getUserId()).build();
        kafkaTemplate.send("post-liked-topic",postId,postLikedEvent);

        log.info("Like posts successful!");
    }

    @Override
    @Transactional
    public void unlikePost(Long postId) {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Attempting to unlike the post with id: {}",postId);
        boolean exists = postsRepository.existsById(postId);
        if(!exists)
            throw new ResourceNotFoundException("Post not found with id: "+postId);
       Boolean postsLikeExists = postsLikeRepository.existsByUserIdAndPostId(userId,postId);
       if (!postsLikeExists)
           throw new BadRequestException("Posts not liked yet!");

       postsLikeRepository.deleteByUserIdAndPostId(userId,postId);
        log.info("Unlike posts successful!");
    }
}

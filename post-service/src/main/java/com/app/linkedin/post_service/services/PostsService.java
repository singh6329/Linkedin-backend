package com.app.linkedin.post_service.services;

import com.app.linkedin.post_service.dtos.PostRequestDto;
import com.app.linkedin.post_service.dtos.PostsDto;

import java.util.List;

public interface PostsService {

    PostsDto createPost(PostRequestDto postRequestDto);

    PostsDto getPostById(Long postId);

    List<PostsDto> getAllPostsOfUser(Long userId);

    void deletePost(Long postId);

    void increaseLikesCounterOfPosts(Long postId);

    void decreaseLikesCounterOfPost(Long postId);

    void increaseCommentsCounterOfPosts(Long postId);

    void decreaseCommentsCounterOfPosts(Long postId);

    Boolean existsByPostId(Long postId);
}

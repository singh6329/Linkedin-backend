package com.app.linkedin.posts_like_service.services;

public interface LikesService {

    void likePost(Long postId);

    void unlikePost(Long postId);
}

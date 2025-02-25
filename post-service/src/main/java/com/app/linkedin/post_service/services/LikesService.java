package com.app.linkedin.post_service.services;

import com.app.linkedin.post_service.dtos.PostsDto;

public interface LikesService {

    void likePost(Long postId);

    void unlikePost(Long postId);
}

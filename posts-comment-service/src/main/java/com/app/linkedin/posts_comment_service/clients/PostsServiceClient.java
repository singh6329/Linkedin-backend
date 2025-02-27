package com.app.linkedin.posts_comment_service.clients;

import com.app.linkedin.posts_comment_service.dtos.PostsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "post-service",path = "/posts/core")
public interface PostsServiceClient {

    @GetMapping("/{postId}")
    PostsDto getPost(@PathVariable(name = "postId") Long postId);

    @GetMapping("/exists/{postId}")
    Boolean existsByPostId(@PathVariable Long postId);
}

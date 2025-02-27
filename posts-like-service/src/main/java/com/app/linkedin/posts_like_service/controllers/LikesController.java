package com.app.linkedin.posts_like_service.controllers;

import com.app.linkedin.posts_like_service.services.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> likePost(@PathVariable(name = "postId") Long postId)
    {
        likesService.likePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> unlikePost(@PathVariable(name = "postId") Long postId)
    {
        likesService.unlikePost(postId);
        return ResponseEntity.noContent().build();
    }

}

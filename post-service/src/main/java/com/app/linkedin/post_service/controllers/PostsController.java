package com.app.linkedin.post_service.controllers;

import com.app.linkedin.post_service.auth.UserContextHolder;
import com.app.linkedin.post_service.dtos.PostRequestDto;
import com.app.linkedin.post_service.dtos.PostsDto;
import com.app.linkedin.post_service.services.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostsDto> getPost(@PathVariable(name = "postId") Long postId)
    {
        return new ResponseEntity<>(postsService.getPostById(postId),HttpStatus.OK);
    }

    @GetMapping("/exists/{postId}")
    public Boolean existsByPostId(@PathVariable Long postId)
    {
        return postsService.existsByPostId(postId);
    }

    @GetMapping("/users/{userId}/get-all-posts")
    public ResponseEntity<List<PostsDto>> getAllPostsOfUser(@PathVariable(name = "userId")Long userId)
    {
        return new ResponseEntity<>(postsService.getAllPostsOfUser(userId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostsDto> createPost(@RequestBody PostRequestDto postRequestDto)
    {
        return new ResponseEntity<>(postsService.createPost(postRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId)
    {
        postsService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

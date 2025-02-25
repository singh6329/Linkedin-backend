package com.app.linkedin.post_service.controllers;

import com.app.linkedin.post_service.dtos.CommentRequestDto;
import com.app.linkedin.post_service.dtos.CommentResponseDto;
import com.app.linkedin.post_service.services.CommentsService;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;
    private final int PAGE_SIZE = 15;

    @GetMapping("/{postId}/get-all-comments")
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsOfPosts(@PathVariable Long postId,
                                                                         @RequestParam(required = false,defaultValue = "0") int pageNumber)
    {
       return ResponseEntity.ok(commentsService.getAllCommentsOfPosts(postId, PageRequest.of(pageNumber,PAGE_SIZE, Sort.by(Sort.Direction.DESC,"createdAt"))));
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Void> addComment(@PathVariable Long postId,
                                            @RequestBody CommentRequestDto commentRequestDto)
    {
        commentsService.addComment(commentRequestDto,postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId)
    {
        commentsService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

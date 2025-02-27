package com.app.linkedin.posts_comment_service.services;

import com.app.linkedin.posts_comment_service.dtos.CommentRequestDto;
import com.app.linkedin.posts_comment_service.dtos.CommentResponseDto;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CommentsService {

    void addComment(CommentRequestDto commentRequestDto, Long postId);
    void deleteComment(Long commentId);
    List<CommentResponseDto> getAllCommentsOfPosts(Long postId, PageRequest pageRequest);
}

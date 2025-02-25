package com.app.linkedin.post_service.services;

import com.app.linkedin.post_service.dtos.CommentRequestDto;
import com.app.linkedin.post_service.dtos.CommentResponseDto;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.List;

public interface CommentsService {

    void addComment(CommentRequestDto commentRequestDto,Long postId);
    void deleteComment(Long commentId);
    List<CommentResponseDto> getAllCommentsOfPosts(Long postId, PageRequest pageRequest);
}

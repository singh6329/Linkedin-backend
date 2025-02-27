package com.app.linkedin.posts_comment_service.repositories;

import com.app.linkedin.posts_comment_service.entities.PostsComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsCommentRepository extends JpaRepository<PostsComment,Long> {

    Page<PostsComment> findAllByPostId(Long postId, PageRequest pageRequest);
}

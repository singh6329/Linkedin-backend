package com.app.linkedin.post_service.repositories;

import com.app.linkedin.post_service.entities.PostsComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsCommentRepository extends JpaRepository<PostsComment,Long> {

    Page<PostsComment> findAllByPostId(Long postId, PageRequest pageRequest);
}

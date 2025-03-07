package com.app.linkedin.post_service.repositories;

import com.app.linkedin.post_service.entities.PostsLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsLikeRepository extends JpaRepository<PostsLike,Long> {

    Boolean existsByUserIdAndPostId(Long userId,Long postId);

    void deleteByUserIdAndPostId(Long userId, Long postId);
}

package com.app.linkedin.post_service.repositories;

import com.app.linkedin.post_service.entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Posts,Long> {
        List<Posts> findAllByUserId(Long userId);
}

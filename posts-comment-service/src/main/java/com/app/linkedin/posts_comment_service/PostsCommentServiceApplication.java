package com.app.linkedin.posts_comment_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PostsCommentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostsCommentServiceApplication.class, args);
	}

}

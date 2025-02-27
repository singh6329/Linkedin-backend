package com.app.linkedin.posts_like_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PostsLikeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostsLikeServiceApplication.class, args);
	}

}

package com.example.redis.domain.model;

import lombok.Getter;

@Getter
public class Post {

	private Long postId;

	private String subject;

	private String content;

	public Post() {
	}

	public Post(String subject, String content) {
		this.subject = subject;
		this.content = content;
	}

	public Post(Long postId, String subject, String content) {
		this.postId = postId;
		this.subject = subject;
		this.content = content;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

}

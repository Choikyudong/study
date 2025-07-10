package com.example.redis.adapter.out.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class PostEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long postId;

	private String subject;

	private String content;

	protected PostEntity() {
	}

	public PostEntity(String subject, String content) {
		this.subject = subject;
		this.content = content;
	}

}

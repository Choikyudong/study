package com.example.nplusone.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Comments {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	private Posts posts;

	private String author;

	private String content;

	protected Comments() {}

	@Builder
	public Comments(long id, String author, String content) {
		this.id = id;
		this.author = author;
		this.content = content;
	}

	public Comments(String author, String content) {
		this.author = author;
		this.content = content;
	}

	public void assignPosts(Posts posts) {
		this.posts = posts;
	}

}

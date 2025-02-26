package com.example.nplusone.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Posts {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String author;

	private String content;

	protected Posts() {}

	@Builder
	public Posts(long id, String author, String content) {
		this.id = id;
		this.author = author;
		this.content = content;
	}

	public Posts(String author, String content) {
		this.author = author;
		this.content = content;
	}

	@BatchSize(size = 20)
	@OneToMany(
			mappedBy = "posts",
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	List<Comments> comments = new ArrayList<>();

	public void addComments(Comments comments) {
		this.comments.add(comments);
		comments.assignPosts(this);
	}

}

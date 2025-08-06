package com.example.graphql;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Book {
	private String id;
	private String title;
	private Author author;
	private LocalDateTime publishAt;

	public Book(String id, String title, Author author) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.publishAt = java.time.LocalDateTime.now();
	}

}

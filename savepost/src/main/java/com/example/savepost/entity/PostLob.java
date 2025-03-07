package com.example.savepost.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLob extends Post {

	@Column(columnDefinition = "TEXT")
	private String content;

	public PostLob(String subject, String content) {
		super(subject);
		this.content = content;
	}

}

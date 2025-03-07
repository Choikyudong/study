package com.example.savepost.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLink extends Post {

	@Column(columnDefinition = "TEXT")
	private String content;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "post_link_id")
	private List<ContentFile> contentFiles = new ArrayList<>();

	public PostLink(String subject, String content) {
		super(subject);
		this.content = content;
	}

	public void addConentFile(ContentFile file) {
		this.contentFiles.add(file);
		file.setPostLink(this);
	}

	public void updateContent(String content) {
		this.content = content;
	}

}

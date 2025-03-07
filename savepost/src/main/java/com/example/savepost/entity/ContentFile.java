package com.example.savepost.entity;

import com.example.savepost.domain.ContentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentFile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JsonIgnore
	private PostLink postLink;

	private String filePath;

	@Enumerated(EnumType.STRING)
	private ContentType contentType;

	public ContentFile(PostLink postLink, String filePath, ContentType contentType) {
		this.postLink = postLink;
		this.filePath = filePath;
		this.contentType = contentType;
	}

	public void setPostLink(PostLink postLink) {
		this.postLink = postLink;
	}

}

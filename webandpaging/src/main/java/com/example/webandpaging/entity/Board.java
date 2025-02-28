package com.example.webandpaging.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(
	name = "board",
	indexes = {@Index(name = "idx_last_modified_at", columnList = "lastModifiedAt")}
)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String author;

	private String subject;

	@Lob
	private String content;

	@CreatedDate
	@Column(updatable = false)
	LocalDateTime createdAt;

	@LastModifiedDate
	LocalDateTime lastModifiedAt;

	@Builder
	public Board(String author, String subject, String content) {
		this.author = author;
		this.subject = subject;
		this.content = content;
	}

}

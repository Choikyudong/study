package com.example.webandpaging.dto;

import com.example.webandpaging.entity.Board;

import java.time.LocalDateTime;

public record BoardRes(
		String author,
		String subject,
		LocalDateTime createdAt,
		LocalDateTime lastModifiedAt) {

	public static BoardRes convert(Board board) {
		return new BoardRes(
				board.getAuthor(),
				board.getSubject(),
				board.getCreatedAt(),
				board.getLastModifiedAt()
		);
	}

}

package com.example.webandpaging.dto;

import com.example.webandpaging.entity.Board;

import java.time.LocalDateTime;

public record BoardSaveRes(
		String author,
		String subject,
		String content,
		LocalDateTime createdAt) {

	public static BoardSaveRes convert(Board board) {
		return new BoardSaveRes(
				board.getAuthor(),
				board.getSubject(),
				board.getContent(),
				board.getCreatedAt()
		);
	}

}

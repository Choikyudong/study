package com.example.webandpaging.dto;

import com.example.webandpaging.entity.Board;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;

public record BoardCursorRes(List<BoardRes> contents, LocalDateTime nextCursor) {

	public static BoardCursorRes convert(Slice<Board> boards) {
		List<BoardRes> contents = boards.getContent().stream()
				.map(BoardRes::convert)
				.toList();
		LocalDateTime nextCursor = contents.isEmpty() ? null
				: contents.get(contents.size() - 1).lastModifiedAt();
		return new BoardCursorRes(contents, nextCursor);
	}

}

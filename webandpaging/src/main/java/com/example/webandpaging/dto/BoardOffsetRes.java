package com.example.webandpaging.dto;

import com.example.webandpaging.entity.Board;
import org.springframework.data.domain.Page;

import java.util.List;

public record BoardOffsetRes(
		List<BoardRes> contents,
		Paging paging) {

	public static BoardOffsetRes convert(Page<Board> boards) {
  		List<BoardRes> contents = boards.getContent().stream()
				.map(BoardRes::convert)
				.toList();

		Paging newPaging = new Paging(
				boards.getPageable().getPageNumber(),
				boards.getPageable().getPageNumber(),
				boards.getTotalPages(),
				boards.hasPrevious(),
				boards.hasNext()
		);

		return new BoardOffsetRes(contents, newPaging);
	}

}

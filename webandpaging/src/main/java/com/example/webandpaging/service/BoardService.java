package com.example.webandpaging.service;

import com.example.webandpaging.dto.BoardCursorRes;
import com.example.webandpaging.dto.BoardOffsetRes;
import com.example.webandpaging.dto.BoardSaveReq;
import com.example.webandpaging.dto.BoardSaveRes;
import com.example.webandpaging.entity.Board;
import com.example.webandpaging.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;

	@Transactional(readOnly = true)
	public BoardOffsetRes getBoardsByOffset(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "lastModifiedAt");
		return BoardOffsetRes.convert(boardRepository.findAll(pageable));
	}

	@Transactional(readOnly = true)
	public BoardCursorRes getBoardsByCursor(String curCursor, int pageSize) {
		LocalDateTime cursor;
		try {
			cursor = LocalDateTime.parse(curCursor);
		} catch (DateTimeParseException d) {
			log.error(d.getMessage());
			cursor = LocalDateTime.MAX; // 날짜가 없을 경우 최신 게시글을 가져온다.
		}

		Pageable pageable = PageRequest.of(0, pageSize);
		Slice<Board> boards =
				boardRepository.findByLastModifiedAtLessThanOrderByLastModifiedAtDesc(cursor, pageable);
		return BoardCursorRes.convert(boards);
	}

	@Transactional
	public BoardSaveRes saveBoard(BoardSaveReq saveReq) {
		Board board = boardRepository.save(Board.builder()
				.author(saveReq.author())
				.subject(saveReq.subject())
				.content(saveReq.content())
				.build());
		return BoardSaveRes.convert(board);
	}

}

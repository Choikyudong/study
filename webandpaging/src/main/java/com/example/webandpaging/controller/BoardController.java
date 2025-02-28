package com.example.webandpaging.controller;

import com.example.webandpaging.dto.BoardCursorRes;
import com.example.webandpaging.dto.BoardOffsetRes;
import com.example.webandpaging.dto.BoardSaveReq;
import com.example.webandpaging.dto.BoardSaveRes;
import com.example.webandpaging.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	@GetMapping("/offset")
	public ResponseEntity<BoardOffsetRes> getBoardsByOffset(
			@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "10") int pageSize) {
		return ResponseEntity.ok(boardService.getBoardsByOffset(pageNumber, pageSize));
	}

	@GetMapping("/cursor")
	public ResponseEntity<BoardCursorRes> getBoardsByCursor(
			@RequestParam(required = false) String cursor,
			@RequestParam(defaultValue = "10") int pageSize) {
		return ResponseEntity.ok(boardService.getBoardsByCursor(cursor, pageSize));
	}

	@PostMapping
	public ResponseEntity<BoardSaveRes> saveBoard(@RequestBody BoardSaveReq boardSaveReq) {
		return ResponseEntity.ok(boardService.saveBoard(boardSaveReq));
	}

}

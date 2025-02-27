package com.example.recordinjava.controller;

import com.example.recordinjava.record.UserSaveReq;
import com.example.recordinjava.record.UserSaveRes;
import com.example.recordinjava.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsersController {

	private final UsersService usersService;

	@PostMapping
	public ResponseEntity<UserSaveRes> save(@RequestBody UserSaveReq saveReq) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usersService.save(saveReq));
	}

}

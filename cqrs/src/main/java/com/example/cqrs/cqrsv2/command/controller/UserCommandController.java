package com.example.cqrs.cqrsv2.command.controller;

import com.example.cqrs.cqrsv2.command.controller.dto.UserCreateRequest;
import com.example.cqrs.cqrsv2.command.controller.dto.UserUpdateRequest;
import com.example.cqrs.cqrsv2.command.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserCommandController {

	private final UserCommandService commandService;

	@PostMapping
	public ResponseEntity<String> createUser(@RequestBody UserCreateRequest request) {
		commandService.createUser(request);
		return ResponseEntity.ok("created");
	}

	@PatchMapping
	public ResponseEntity<String> updateUser(@RequestBody UserUpdateRequest request) {
		commandService.updateUser(request);
		return ResponseEntity.ok("updated");
	}

}

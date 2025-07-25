package com.example.cqrs.cqrsv1.command.controller;

import com.example.cqrs.cqrsv1.command.service.UserCommandService;
import com.example.cqrs.notcqrs.domain.Address;
import com.example.cqrs.notcqrs.domain.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserCommandController {

	private final UserCommandService commandService;

	@PostMapping
	public ResponseEntity<String> createUser(@RequestParam String firstName, @RequestParam String lastName) {
		commandService.createUser(firstName, lastName);
		return ResponseEntity.ok("created");
	}

	@PatchMapping
	public ResponseEntity<String> updateUser(Long userId, Set<Contact> contacts, Set<Address> addresses) {
		commandService.updateUser(userId, contacts, addresses);
		return ResponseEntity.ok("updated");
	}

}

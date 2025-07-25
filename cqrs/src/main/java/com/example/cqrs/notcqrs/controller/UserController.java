package com.example.cqrs.notcqrs.controller;

import com.example.cqrs.notcqrs.domain.Address;
import com.example.cqrs.notcqrs.domain.Contact;
import com.example.cqrs.notcqrs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService service;

	@GetMapping("/{userId}/contact/{contactType}")
	public ResponseEntity<Set<Contact>> getContactByType(@PathVariable Long userId, @PathVariable String contactType) {
		return ResponseEntity.ok(service.getContactByType(userId, contactType));
	}

	@GetMapping("/{userId}/address/{state}")
	public ResponseEntity<Set<Address>> getAddressByRegion(@PathVariable Long userId, @PathVariable String state) {
		return ResponseEntity.ok(service.getAddressByRegion(userId, state));
	}

	@PostMapping
	public ResponseEntity<String> createUser(@RequestParam String firstName, @RequestParam String lastName) {
		service.createUser(firstName, lastName);
		return ResponseEntity.ok("created");
	}

	@PatchMapping
	public ResponseEntity<String> updateUser(Long userId, Set<Contact> contacts, Set<Address> addresses) {
		service.updateUser(userId, contacts, addresses);
		return ResponseEntity.ok("updated");
	}

}

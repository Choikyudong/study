package com.example.cqrs.cqrsv2.query.controller;

import com.example.cqrs.cqrsv1.query.controller.dto.AddressDto;
import com.example.cqrs.cqrsv1.query.controller.dto.ContactDto;
import com.example.cqrs.cqrsv1.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserQueryController {

	private final UserQueryService service;

	@GetMapping("/{userId}/contact/{contactType}")
	public ResponseEntity<Set<ContactDto>> getContactByType(@PathVariable Long userId, @PathVariable String contactType) {
		return ResponseEntity.ok(service.getContactByType(userId, contactType));
	}

	@GetMapping("/{userId}/address/{state}")
	public ResponseEntity<Set<AddressDto>> getAddressByRegion(@PathVariable Long userId, @PathVariable String state) {
		return ResponseEntity.ok(service.getAddressByRegion(userId, state));
	}

}

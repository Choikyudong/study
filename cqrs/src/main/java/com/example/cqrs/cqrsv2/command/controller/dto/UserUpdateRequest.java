package com.example.cqrs.cqrsv2.command.controller.dto;

import com.example.cqrs.notcqrs.domain.Address;
import com.example.cqrs.notcqrs.domain.Contact;

import java.util.Set;

public record UserUpdateRequest(
		Long userId,
		Set<Contact> contacts,
		Set<Address> addresses
) {
}

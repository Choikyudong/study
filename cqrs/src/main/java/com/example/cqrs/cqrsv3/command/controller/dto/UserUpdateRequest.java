package com.example.cqrs.cqrsv3.command.controller.dto;

import java.util.Set;

public record UserUpdateRequest(
		Long userId,
		Set<String> contacts,
		Set<String> addresses
) {
}

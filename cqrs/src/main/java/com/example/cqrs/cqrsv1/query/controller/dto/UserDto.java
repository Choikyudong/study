package com.example.cqrs.cqrsv1.query.controller.dto;

public record UserDto(
		Long userId,
		String firstName,
		String lastName
) {
}

package com.example.cqrs.cqrsv3.query.controller.dto;

public record UserDto(
		Long userId,
		String firstName,
		String lastName
) {
}

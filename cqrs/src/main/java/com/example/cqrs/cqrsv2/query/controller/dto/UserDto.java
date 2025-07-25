package com.example.cqrs.cqrsv2.query.controller.dto;

public record UserDto(
		Long userId,
		String firstName,
		String lastName
) {
}

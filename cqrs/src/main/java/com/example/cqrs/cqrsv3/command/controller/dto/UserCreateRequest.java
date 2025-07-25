package com.example.cqrs.cqrsv3.command.controller.dto;

public record UserCreateRequest(
		String firstName,
		String lastName
) {
}

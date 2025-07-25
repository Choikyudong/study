package com.example.cqrs.cqrsv2.command.controller.dto;

public record UserCreateRequest(
		String firstName,
		String lastName
) {
}

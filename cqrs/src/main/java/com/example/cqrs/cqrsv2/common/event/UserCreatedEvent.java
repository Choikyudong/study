package com.example.cqrs.cqrsv2.common.event;

public record UserCreatedEvent(
		Long userId,
		String firstName,
		String lastName
) {
}

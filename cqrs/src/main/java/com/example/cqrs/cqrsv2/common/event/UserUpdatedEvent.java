package com.example.cqrs.cqrsv2.common.event;

import com.example.cqrs.notcqrs.domain.Address;
import com.example.cqrs.notcqrs.domain.Contact;

import java.util.Set;

public record UserUpdatedEvent(
		Long userId,
		Set<Contact> contacts,
		Set<Address> addresses
) {
}

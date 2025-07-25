package com.example.cqrs.cqrsv3.query.controller.dto;

public record ContactDto(
		Long contactId,
		String type,
		String detail
) {
	public ContactDto(Long contactId, String type, String detail) {
		this.contactId = contactId;
		this.type = type;
		this.detail = detail;
	}
}

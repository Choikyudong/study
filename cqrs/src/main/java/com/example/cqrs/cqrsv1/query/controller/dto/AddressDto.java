package com.example.cqrs.cqrsv1.query.controller.dto;

public record AddressDto(
		Long addressId,
		String city,
		String state,
		String postcode
) {
	public AddressDto(Long addressId, String city, String state, String postcode) {
		this.addressId = addressId;
		this.city = city;
		this.state = state;
		this.postcode = postcode;
	}
}

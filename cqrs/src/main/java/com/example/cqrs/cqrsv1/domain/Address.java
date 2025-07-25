package com.example.cqrs.cqrsv1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "Address")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

	@Id
	private Long addressId;

	@ManyToOne
	private User user;

	private String city;

	private String state;

	private String postcode;

	public Address(String city, String state, String postcode) {
		this.city = city;
		this.state = state;
		this.postcode = postcode;
	}

}

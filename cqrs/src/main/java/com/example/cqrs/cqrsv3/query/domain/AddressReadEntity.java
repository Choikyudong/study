package com.example.cqrs.cqrsv3.query.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "ADDRESS_READ_VIEW")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressReadEntity {

	@Id
	private Long addressId;

	@ManyToOne
	private UserReadEntity user;

	private String city;

	private String state;

	private String postcode;

	public AddressReadEntity(String city, String state, String postcode) {
		this.city = city;
		this.state = state;
		this.postcode = postcode;
	}

	public void setUser(UserReadEntity user) {
		this.user = user;
	}

}

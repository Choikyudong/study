package com.example.cqrs.cqrsv3.command.domain;

import com.example.cqrs.cqrsv2.command.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "ADDRESES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

	@Id
	private Long addressId;

	@ManyToOne
	private com.example.cqrs.cqrsv2.command.domain.User user;

	private String city;

	private String state;

	private String postcode;

	public Address(String city, String state, String postcode) {
		this.city = city;
		this.state = state;
		this.postcode = postcode;
	}

	public void setUser(User user) {
		this.user = user;
	}

}

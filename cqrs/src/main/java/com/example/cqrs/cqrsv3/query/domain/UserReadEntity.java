package com.example.cqrs.cqrsv3.query.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS_READ_VIEW")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserReadEntity {

	@Id
	private Long userId;
	private String firstName;
	private String lastName;

	@OneToMany(
			mappedBy = "user",
			fetch = FetchType.LAZY
	)
	private Set<ContactReadEntity> contacts = new HashSet<>();

	@OneToMany(
			mappedBy = "user",
			fetch = FetchType.LAZY
	)
	private Set<AddressReadEntity> addresses = new HashSet<>();

	public UserReadEntity(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public void updateFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void updateLastName(String lastName) {
		this.lastName = lastName;
	}

	public void changeContacts(Set<ContactReadEntity> newContacts) {
		this.contacts = newContacts;
	}

	public void changeAddress(Set<AddressReadEntity> newAddress) {
		this.addresses = newAddress;
	}

}

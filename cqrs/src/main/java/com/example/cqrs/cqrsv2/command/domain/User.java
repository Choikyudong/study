package com.example.cqrs.cqrsv2.command.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	private Long userId;
	private String firstName;
	private String lastName;

	@OneToMany(
			mappedBy = "user",
			fetch = FetchType.LAZY
	)
	private Set<Contact> contacts = new HashSet<>();

	@OneToMany(
			mappedBy = "user",
			fetch = FetchType.LAZY
	)
	private Set<Address> addresses = new HashSet<>();

	public User(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public void updateFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void updateLastName(String lastName) {
		this.lastName = lastName;
	}

	public void changeContacts(Set<Contact> newContacts) {
		this.contacts = newContacts;
	}

	public void changeAddress(Set<Address> newAddress) {
		this.addresses = newAddress;
	}

}

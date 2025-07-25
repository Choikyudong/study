package com.example.cqrs.notcqrs.service;

import com.example.cqrs.notcqrs.domain.User;
import com.example.cqrs.notcqrs.domain.Address;
import com.example.cqrs.notcqrs.domain.Contact;
import com.example.cqrs.notcqrs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository repository;

	public void createUser(String firstName, String lastName) {
		User user = new User(firstName, lastName);
		repository.save(user);
	}

	public void updateUser(Long userId, Set<Contact> contacts, Set<Address> addresses) {
		User user = repository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("에러"));
		user.changeContacts(contacts);
		user.changeAddress(addresses);
		repository.save(user);
	}

	public Set<Contact> getContactByType(Long userId, String contactType) {
		User user = repository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("에러"));
		Set<Contact> contacts = user.getContacts();
		return contacts.stream()
				.filter(c -> c.getType().equals(contactType))
				.collect(Collectors.toSet());
	}

	public Set<Address> getAddressByRegion(Long userId, String state) {
		User user = repository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("에러"));
		Set<Address> addresses = user.getAddresses();
		return addresses.stream()
				.filter(a -> a.getState().equals(state))
				.collect(Collectors.toSet());
	}

}

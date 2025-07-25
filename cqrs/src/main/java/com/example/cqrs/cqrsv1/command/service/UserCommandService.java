package com.example.cqrs.cqrsv1.command.service;

import com.example.cqrs.cqrsv1.command.repository.UserCommandRepository;
import com.example.cqrs.notcqrs.domain.User;
import com.example.cqrs.notcqrs.domain.Address;
import com.example.cqrs.notcqrs.domain.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserCommandService {

	private final UserCommandRepository commandRepository;

	public void createUser(String firstName, String lastName) {
		User user = new User(firstName, lastName);
		commandRepository.save(user);
	}

	public void updateUser(Long userId, Set<Contact> contacts, Set<Address> addresses) {
		User user = commandRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("에러"));
		user.changeContacts(contacts);
		user.changeAddress(addresses);
		commandRepository.save(user);
	}

}

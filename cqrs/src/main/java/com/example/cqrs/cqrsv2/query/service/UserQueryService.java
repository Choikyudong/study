package com.example.cqrs.cqrsv2.query.service;

import com.example.cqrs.cqrsv2.query.controller.dto.AddressDto;
import com.example.cqrs.cqrsv2.query.controller.dto.ContactDto;
import com.example.cqrs.cqrsv2.query.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {

	private final UserQueryRepository repository;

	public Set<ContactDto> getContactByType(Long userId, String contactType) {
		Set<ContactDto> contacts = repository.findContactByUserId(userId);
		return contacts.stream()
				.filter(c -> c.type().equals(contactType))
				.collect(Collectors.toSet());
	}

	public Set<AddressDto> getAddressByRegion(Long userId, String state) {
		Set<AddressDto> addresses = repository.findAddressByUserId(userId);
		return addresses.stream()
				.filter(a -> a.state().equals(state))
				.collect(Collectors.toSet());
	}

}

package com.example.cqrs.cqrsv2.command.service;

import com.example.cqrs.cqrsv2.command.controller.dto.UserCreateRequest;
import com.example.cqrs.cqrsv2.command.controller.dto.UserUpdateRequest;
import com.example.cqrs.cqrsv2.command.repository.UserCommandRepository;
import com.example.cqrs.cqrsv2.common.event.UserCreatedEvent;
import com.example.cqrs.cqrsv2.common.event.UserUpdatedEvent;
import com.example.cqrs.notcqrs.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandService {

	private final UserCommandRepository commandRepository;
	private final ApplicationEventPublisher eventPublisher;

	@Transactional("writeTransactionManager")
	public void createUser(UserCreateRequest request) {
		User user = new User(request.firstName(), request.lastName());
		commandRepository.save(user);

		eventPublisher.publishEvent(new UserCreatedEvent(
				user.getUserId(), user.getFirstName(), user.getFirstName()
		));
	}

	@Transactional("writeTransactionManager")
	public void updateUser(UserUpdateRequest request) {
		User user = commandRepository.findById(request.userId())
				.orElseThrow(() -> new IllegalArgumentException("에러"));
		user.changeContacts(request.contacts());
		user.changeAddress(request.addresses());
		commandRepository.save(user);

		eventPublisher.publishEvent(new UserUpdatedEvent(
				user.getUserId(), user.getContacts(), user.getAddresses()
		));
	}

}

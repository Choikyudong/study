package com.example.cqrs.cqrsv3.command.service;

import com.example.cqrs.cqrsv3.command.controller.dto.UserCreateRequest;
import com.example.cqrs.cqrsv3.command.controller.dto.UserUpdateRequest;
import com.example.cqrs.cqrsv3.command.domain.User;
import com.example.cqrs.cqrsv3.command.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandService {

	private final UserRepository userRepository;
	private final ApplicationEventPublisher eventPublisher;

	@Transactional("writeTransactionManager")
	public void createUser(UserCreateRequest request) {
		User user = User.createUser(request.firstName(), request.lastName());
		userRepository.save(user);

		user.getUncommittedEvents().forEach(eventPublisher::publishEvent);
	}

	@Transactional("writeTransactionManager")
	public void updateUser(UserUpdateRequest request) {
		User user = userRepository.findById(request.userId())
				.orElseThrow(() -> new IllegalArgumentException("에러"));
		user.updateUser(request.contacts(), request.addresses());
		userRepository.save(user);

		user.getUncommittedEvents().forEach(eventPublisher::publishEvent);
	}

}

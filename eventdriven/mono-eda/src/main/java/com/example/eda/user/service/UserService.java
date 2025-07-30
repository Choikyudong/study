package com.example.eda.user.service;

import com.example.eda.user.event.UserCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final ApplicationEventPublisher eventPublisher;
	// private final UserEventRepostiory eventRepostiory;

	public void createdUser(String userName, String email) {
		log.info("이벤트 전달");
		UserCreateEvent event = new UserCreateEvent(this, userName, email);
		// eventRepostiory.save(event);
		eventPublisher.publishEvent(event);
	}

}

package com.example.eda.email.service;

import com.example.eda.user.event.UserCreateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

	@EventListener
	public void sendWelcomeEmail(UserCreateEvent createEvent) {
		log.info("이벤트 전달 완료 : {}", createEvent);
	}

}

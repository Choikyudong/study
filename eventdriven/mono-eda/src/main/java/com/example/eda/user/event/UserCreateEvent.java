package com.example.eda.user.event;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.time.Instant;

@Getter
@ToString
public class UserCreateEvent extends ApplicationEvent {

	private String userName;
	private String email;
	private Instant createTime;

	public UserCreateEvent(Object source, String userName, String email) {
		super(source);
		this.userName = userName;
		this.email = email;
		this.createTime = Instant.now();
	}

}

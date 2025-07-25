package com.example.cqrs.cqrsv3.common.event;

import lombok.Getter;

import java.time.Instant;

@Getter
public class UserCreatedEvent implements DomainEvent {

	private Long aggregateId;
	private int version;
	private Instant timestamp; // 이벤트 발생 시각
	private String firstName;
	private String lastName;

	public UserCreatedEvent(Long aggregateId, int version, String firstName, String lastName) {
		this.aggregateId = aggregateId;
		this.version = version;
		this.timestamp = Instant.now();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String getEventType() {
		return "UserCreatedEvent"; // 이벤트의 고유한 문자열 이름 반환
	}

}

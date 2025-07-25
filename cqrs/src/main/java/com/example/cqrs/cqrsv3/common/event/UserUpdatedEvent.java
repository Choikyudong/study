package com.example.cqrs.cqrsv3.common.event;

import lombok.Getter;

import java.time.Instant;
import java.util.Set;

@Getter
public class UserUpdatedEvent implements DomainEvent {

	private Long aggregateId;
	private int version;
	private Instant timestamp; // 이벤트 발생 시각
	private Set<String> contacts;
	private Set<String> addresses;

	public UserUpdatedEvent(Long aggregateId, int version, Set<String> contacts, Set<String> addresses) {
		this.aggregateId = aggregateId;
		this.version = version;
		this.contacts = contacts;
		this.addresses = addresses;
		this.timestamp = Instant.now(); // 이벤트가 생성되는 시점의 시간을 기록
	}

	@Override
	public String getEventType() {
		return "UserUpdatedEvent"; // 이벤트의 고유한 문자열 이름 반환
	}

}

package com.example.cqrs.cqrsv3.common.event;

import java.time.Instant;

public interface DomainEvent {

	// 이벤트가 속한 애그리거트의 ID (예: User의 ID)
	Long getAggregateId();

	// 이 이벤트가 발생했을 때의 애그리거트의 버전
	int getVersion();

	// 이벤트가 발생한 시각
	Instant getTimestamp();

	// 이벤트의 타입을 나타내는 문자열 (예: "UserCreatedEvent")
	String getEventType();

}

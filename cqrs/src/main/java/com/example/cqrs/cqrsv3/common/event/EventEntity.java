package com.example.cqrs.cqrsv3.common.event;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Table(name = "EVENT_STORE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private Long eventId;

	private Long aggregateId;

	private String aggregateType;

	private String eventType;

	@Lob
	private String eventPayload;

	private Instant eventTimestamp;

	private int version; // 낙관적 락에 사용

	public EventEntity(Long aggregateId, String aggregateType, String eventType, String eventPayload, int version) {
		this.aggregateId = aggregateId;
		this.aggregateType = aggregateType;
		this.eventType = eventType;
		this.eventPayload = eventPayload;
		this.eventTimestamp = Instant.now(); // 이벤트 생성 시각은 현재 시간으로 자동 설정
		this.version = version;
	}

}

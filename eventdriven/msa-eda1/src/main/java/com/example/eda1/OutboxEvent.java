package com.example.eda1;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OutboxEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String entityType; 	  // 이벤트가 발생한 도메인 타입 (예: "MyEntity")

	@Column(nullable = false)
	private String entityId;   	  // 이벤트가 발생한 도메인 엔티티의 ID (예: MyEntity의 ID)

	@Column(nullable = false)
	private String eventType;     // 이벤트 타입 (예: "MyEntityCreatedEvent")

	@Column(columnDefinition = "TEXT", nullable = false)
	private String payload;       // JSON 형태의 실제 이벤트 데이터

	@Enumerated(EnumType.STRING)
	private EventStatus status;   // 이벤트 전송 상태 (PENDING, PROCESSED, FAILED)

	private Instant createdAt;

	private Instant processedAt;

	public OutboxEvent(String entityType, String entityId, String eventType, String payload) {
		this.entityType = entityType;
		this.entityId = entityId;
		this.eventType = eventType;
		this.payload = payload;
		this.status = EventStatus.PENDING; // 초기 상태: 전송 대기
		this.createdAt = Instant.now();
	}

	public void markAsProcessed() {
		this.status = EventStatus.PROCESSED;
		this.processedAt = Instant.now();
	}

	// 전송 여부
	public enum EventStatus {
		PENDING, PROCESSED, FAILED
	}

}

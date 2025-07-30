package com.example.eda2;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProcessedEvent {

	@Id
	private Long eventId;

	private Instant processedAt;

	public ProcessedEvent(Long eventId) {
		this.eventId = eventId;
		this.processedAt = Instant.now();
	}

}

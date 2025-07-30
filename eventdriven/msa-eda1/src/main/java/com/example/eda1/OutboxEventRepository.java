package com.example.eda1;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {
	List<OutboxEvent> findTop100ByStatusOrderByCreatedAtDesc(OutboxEvent.EventStatus status);
}

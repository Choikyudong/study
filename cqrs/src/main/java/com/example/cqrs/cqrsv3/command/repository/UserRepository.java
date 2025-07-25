package com.example.cqrs.cqrsv3.command.repository;

import com.example.cqrs.cqrsv3.command.domain.User;
import com.example.cqrs.cqrsv3.common.event.DomainEvent;
import com.example.cqrs.cqrsv3.common.event.EventEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {

	private final EventStoreRepository eventStoreRepository; // EventEntity를 저장하는 JPA Repository
	private final ObjectMapper objectMapper; // DomainEvent 객체를 JSON으로 직렬화/역직렬화하기 위함

	@Transactional("writeTransactionManager")
	public void save(User user) {
		// User 애그리거트가 가지고 있는, 아직 Event Store에 저장되지 않은 이벤트들을 가져옵니다.
		List<DomainEvent> uncommittedEvents = user.getUncommittedEvents();

		if (uncommittedEvents.isEmpty()) {
			log.info("No uncommitted events to save for user {}", user.getUserId());
			return; // 저장할 이벤트가 없으면 종료
		}

		// --- 낙관적 락(Optimistic Locking) 검사 ---
		// 현재 애그리거트의 버전보다 큰 버전의 이벤트가 이미 Event Store에 저장되어 있는지 확인합니다.
		// 이는 동시에 여러 요청이 같은 애그리거트를 변경하려 할 때 충돌을 감지하는 메커니즘입니다.
		// 만약 더 높은 버전의 이벤트가 이미 있다면, 현재 트랜잭션은 실패해야 합니다.
		if (eventStoreRepository.existsByAggregateIdAndVersionGreaterThan(user.getUserId(), user.getVersion())) {
			throw new OptimisticLockingFailureException(
					"Concurrent modification detected for User with ID: " + user.getUserId() +
							". Expected version: " + user.getVersion() + " but a newer version already exists."
			);
		}

		// DomainEvent 객체들을 EventEntity 객체로 변환합니다.
		// EventEntity는 데이터베이스의 event_store 테이블에 매핑되는 JPA 엔티티입니다.
		List<EventEntity> eventEntities = uncommittedEvents.stream()
				.map(event -> {
					try {
						// DomainEvent 객체를 JSON 문자열로 직렬화하여 event_payload에 저장합니다.
						String payload = objectMapper.writeValueAsString(event);
						return new EventEntity(
								event.getAggregateId(),
								"User", // 애그리거트 타입 (하드코딩 대신 Enum 사용도 고려)
								event.getEventType(), // 이벤트 타입 (예: "UserCreatedEvent")
								payload,
								event.getVersion()
						);
					} catch (JsonProcessingException e) {
						// JSON 직렬화 실패 시 런타임 예외 발생
						throw new RuntimeException("Failed to serialize event: " + event.getEventType(), e);
					}
				})
				.collect(Collectors.toList());

		// 변환된 EventEntity들을 Event Store에 저장합니다.
		eventStoreRepository.saveAll(eventEntities);

		// 이벤트들이 성공적으로 저장되었으므로, User 애그리거트의 uncommittedEvents 목록을 비웁니다.
		user.clearUncommittedEvents();
		log.info("Saved {} events for user {}", eventEntities.size(), user.getUserId());
	}

	// 특정 User ID에 해당하는 User 애그리거트를 Event Store의 이력으로부터 재구성하여 반환합니다.
	public Optional<User> findById(Long userId) {
		// Event Store에서 해당 aggregateId에 해당하는 모든 이벤트를 버전 순서대로 조회합니다.
		List<EventEntity> eventEntities = eventStoreRepository.findByAggregateIdOrderByVersionAsc(userId);

		if (eventEntities.isEmpty()) {
			return Optional.empty(); // 해당 User의 이벤트 이력이 없으면 User도 존재하지 않음
		}

		// 조회된 EventEntity들을 DomainEvent 객체로 역직렬화합니다.
		List<DomainEvent> historyEvents = eventEntities.stream()
				.map(eventEntity -> {
					try {
						// 이벤트 타입 문자열(eventEntity.getEventType())을 사용하여
						// 해당 이벤트 클래스(예: UserCreatedEvent.class)를 동적으로 로드합니다.
						// 이 부분은 실제 프로젝트에서는 더 견고한 매핑 전략이 필요할 수 있습니다 (예: Map<String, Class<? extends DomainEvent>>).
						Class<? extends DomainEvent> eventClass = (Class<? extends DomainEvent>) Class.forName(
								"com.example.cqrs.cqrsv3.common.event." + eventEntity.getEventType()
						);
						// JSON 문자열을 해당 이벤트 클래스의 객체로 역직렬화합니다.
						return objectMapper.readValue(eventEntity.getEventPayload(), eventClass);
					} catch (Exception e) { // ClassNotFoundException, JsonProcessingException 등
						log.error("Failed to deserialize event: {}", eventEntity.getEventType(), e);
						throw new RuntimeException("Failed to load user from events", e);
					}
				})
				.collect(Collectors.toList());

		// 역직렬화된 DomainEvent 목록을 User.reconstruct() 메서드에 전달하여
		// User 애그리거트의 현재 상태를 재구성합니다.
		return Optional.of(User.reconstruct(historyEvents));
	}

}

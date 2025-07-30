package com.example.eda1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyService {

	private final MyRepository myRepository;
	private final OutboxEventRepository eventRepository;
	private final ObjectMapper objectMapper;

	@Transactional
	public void startEvent() {
		MyEntity myEntity = new MyEntity();

		log.info("영속화 시작");
		myRepository.save(myEntity);

		log.info("이벤트 전송");

		// 2. 이벤트를 Kafka로 직접 보내는 대신, Outbox 테이블에 저장
		try {
			String payload = objectMapper.writeValueAsString(myEntity);
			OutboxEvent outboxEvent = new OutboxEvent(
					MyEntity.class.getName(),
					String.valueOf(myEntity.getId()), // entityId
					"MyEntityCreated",          	  // eventType (이벤트 종류를 명확히)
					payload                     	  // 실제 이벤트 데이터 (JSON)
			);
			eventRepository.save(outboxEvent); // Outbox 테이블에 이벤트 저장
			log.info("Outbox에 이벤트 저장 완료: {}", myEntity);
		} catch (JsonProcessingException e) {
			log.error("MyEntity 직렬화 실패: {}", myEntity, e);
			throw new RuntimeException("이벤트 직렬화 실패, 트랜잭션 롤백", e);
		}

		log.info("EDA1 종료");
	}

	@Transactional(readOnly = true)
	public MyEntity getEntity(Long id) {
		return myRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("아이디 잘못됨"));
	}

}

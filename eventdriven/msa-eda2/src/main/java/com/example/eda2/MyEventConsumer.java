package com.example.eda2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyEventConsumer {

	private final MyService service;
	private final ObjectMapper objectMapper;

	@KafkaListener(topics = "my-event", groupId = "test-group")
	public void consumer(String event) {
		try {
			log.info("이벤트 수신 : {}", event);
			MyEntity myEntity = objectMapper.readValue(event, MyEntity.class);
			service.afterEvent(myEntity);
		} catch (JsonProcessingException j) {
			log.error("Json 역직렬화 실패 : {}", event);
			throw new RuntimeException("이벤트 역직렬화 중 오류 발생", j);
		} catch (Exception e) {
			log.error("이벤트 처리 중 문제 발생 event={}, exception={}", event, e.getMessage());
			throw new RuntimeException("이벤트 처리 중 오류 발생", e);
		}
	}

}

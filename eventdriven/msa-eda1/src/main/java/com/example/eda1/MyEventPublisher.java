package com.example.eda1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyEventPublisher {

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	public void publish(MyEntity myEntity) {
		try {
			String data = objectMapper.writeValueAsString(myEntity);
			kafkaTemplate.send("my-event", data);
			log.info("이벤트 발행 : {}", myEntity);
		} catch (JsonProcessingException j) {
			log.error("Json 직렬화 실패 : {}", myEntity);
			throw new RuntimeException("이벤트 직렬화 중 오류 발생", j);
		}
	}

}

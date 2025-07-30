package com.example.eda1;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxRelayService {

	private final OutboxEventRepository eventRepository;
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	@Transactional
	@Scheduled(fixedDelay = 5000)
	public void publishOutboxEvents() {
		log.debug("Outbox 이벤트 전송 스케줄러 실행!..");
		List<OutboxEvent> pendingEvents = eventRepository.findTop100ByStatusOrderByCreatedAtDesc(OutboxEvent.EventStatus.PENDING);

		if (pendingEvents.isEmpty()) {
			log.debug("전송할 이벤트가 없습니다...");
			return;
		}

		for (OutboxEvent event : pendingEvents) {
			try {
				String data = objectMapper.writeValueAsString(event);
				kafkaTemplate.send("my-event", event.getEventType(), event.getPayload());
				event.markAsProcessed();
				eventRepository.save(event);
				log.info("이벤트 전송 및 상태 업데이트 완료 : Event ID={}", event.getId());
			} catch (JsonParseException j) {
				log.error("이벤트 전송 실패 : Event ID={}, Payload={}", event.getId(), event.getPayload());
				throw new RuntimeException("파싱 실패");
			} catch (Exception e) {
				log.error("이벤트 전송 실패 : Event ID={}, Payload={}", event.getId(), event.getPayload());
				throw new RuntimeException("이벤트 전송 실패");
			}
		}
	}

}

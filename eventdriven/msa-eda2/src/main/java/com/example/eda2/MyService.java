package com.example.eda2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyService {

	private final MyRepository myRepository;
	private final ProcessedEventRepository processedEventRepository;

	@Transactional
	public void afterEvent(MyEntity myEntity) {
		if (processedEventRepository.findById(myEntity.getEventId()).isPresent()) {
			log.info("이미 완료된 이벤트입니다. eventId: {}", myEntity.getEventId());
			return;
		}

		log.info("영속화 시작");
		myRepository.save(myEntity);

		processedEventRepository.save(new ProcessedEvent(myEntity.getEventId()));
		log.info("EDA2 종료");
	}

	@Transactional(readOnly = true)
	public MyEntity getEntity(Long id) {
		return myRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("아이디 잘못됨"));
	}

}

package com.example.kafka1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyService {

	private final MyRepository myRepository;
	private final MyEventPublisher eventPublisher;

	@Transactional
	public void startEvent() {
		MyEntity myEntity = new MyEntity();
		eventPublisher.publish(myRepository.save(myEntity));
	}

	@Transactional
	public void logs() {
		eventPublisher.publishLog();
	}

	@Transactional(readOnly = true)
	public MyEntity getEntity(Long id) {
		return myRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("아이디 잘못됨"));
	}

}

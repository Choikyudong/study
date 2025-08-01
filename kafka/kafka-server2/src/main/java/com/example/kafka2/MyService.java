package com.example.kafka2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyService {

	private final MyRepository myRepository;

	@Transactional
	public void afterEvent(MyEntity myEntity) {
		myRepository.save(myEntity);
	}

	@Transactional(readOnly = true)
	public MyEntity getEntity(Long id) {
		return myRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("아이디 잘못됨"));
	}

}

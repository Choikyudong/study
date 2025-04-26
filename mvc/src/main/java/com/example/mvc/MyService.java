package com.example.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyService {

	private final MyRepository myRepository;

	public String getText(long id) {
		return myRepository.findById(id)
				.map(MyEntity::getText)
				.orElseThrow(IllegalAccessError::new);
	}

	public void saveText(String text) {
		MyEntity myEntity = new MyEntity(text);
		myRepository.save(myEntity);
	}

}

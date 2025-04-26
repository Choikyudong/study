package com.example.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MyController {

	private final MyRepository myRepository;

	@GetMapping("{id}")
	public ResponseEntity<String> helloWorld(@PathVariable long id) {
		String text = myRepository.findById(id)
				.map(MyEntity::getText)
				.orElseThrow(IllegalAccessError::new);
		return ResponseEntity.ok(text);
	}

	@PostMapping
	public ResponseEntity<String> saveHelloWorld(@RequestBody String text) {
		MyEntity myEntity = new MyEntity(text);
		myRepository.save(myEntity);
		return ResponseEntity.ok().build();
	}

}

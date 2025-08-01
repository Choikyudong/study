package com.example.kafka2;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyController {

	private final MyService service;

	@GetMapping("/{id}")
	public ResponseEntity<MyEntity> getEntity(@PathVariable Long id) {
		return ResponseEntity.ok(service.getEntity(id));
	}

}

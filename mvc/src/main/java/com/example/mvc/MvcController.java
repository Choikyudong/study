package com.example.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MvcController {

	private final MyService myService;

	@GetMapping("{id}")
	public ResponseEntity<String> getText(@PathVariable long id) {
		return ResponseEntity.ok(myService.getText(id));
	}

	@PostMapping
	public ResponseEntity<String> saveText(@RequestBody String text) {
		myService.saveText(text);
		return ResponseEntity.ok().build();
	}

}

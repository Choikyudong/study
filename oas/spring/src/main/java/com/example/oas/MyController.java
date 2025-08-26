package com.example.oas;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class MyController {

	@GetMapping
	public ResponseEntity<String> getRequset() {
		return ResponseEntity.ok("Get Ok!");
	}

	@PostMapping
	public ResponseEntity<String> postRequset(@RequestBody PostReqDto postReqDto) {
		return ResponseEntity.created(URI.create("/"))
				.body(String.format("Post %d Ok!", postReqDto.num()));
	}

}

package com.example.cbor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CborController {

	private final ObjectMapper cborObjectMapper;

	@GetMapping(value = "/cbor", produces = "application/cbor")
	public Map<String, Object> getCborData() {
		Map<String, Object> data = new HashMap<>();
		data.put("name", "John");
		data.put("age", 30);
		return data;
	}

	@GetMapping(value = "/json", produces = "application/json")
	public Map<String, Object> getJsonData() {
		Map<String, Object> data = new HashMap<>();
		data.put("name", "John");
		data.put("age", 30);
		return data;
	}

	@PostMapping(value = "/data", consumes = "application/cbor", produces = "application/cbor")
	public Map<String, Object> postCborData(@RequestBody Map<String, Object> input) {
		return input; // 받은 CBOR 데이터를 그대로 반환
	}

}

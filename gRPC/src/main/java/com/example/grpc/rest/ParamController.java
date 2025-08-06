package com.example.grpc.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ParamController {

	@PostMapping
	public ResponseEntity<ResponseDto> sayHello(@RequestBody RequestDto requestDto) {
		Long id = requestDto.id();
		if (id < 1) {
			throw new RuntimeException("에러!");
		}

		List<MessageDetailDto> messageDetails = List.of(
				new MessageDetailDto(id, "콘텐츠1:" + requestDto.myEnum().name(), LocalDateTime.now()),
				new MessageDetailDto(id, "콘텐츠2:" + requestDto.myEnum().name(), LocalDateTime.now())
		);

		ResponseDto responseDto = new ResponseDto(
				requestDto.text() + id,
				messageDetails
		);

		return ResponseEntity.ok(responseDto);
	}

}

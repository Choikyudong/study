package com.example.grpc.webflux;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class StreamController {

	@GetMapping(value = "/server", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> serverStream(@RequestParam long times) {
		return Flux.interval(Duration.ofSeconds(1))
				.take(times)
				.map(seq -> "스트리밍 메시지 #" + seq + 1);
	}

}

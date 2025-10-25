package com.example.elk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping
	public String test() {
		logger.info("info 입니다");
		logger.warn("warning 입니다");
		logger.error("error 입니다");

		return "Hello ELK";
	}

}

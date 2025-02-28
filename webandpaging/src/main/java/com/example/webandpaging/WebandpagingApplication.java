package com.example.webandpaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WebandpagingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebandpagingApplication.class, args);
	}

}

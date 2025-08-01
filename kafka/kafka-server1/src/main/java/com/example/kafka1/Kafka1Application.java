package com.example.kafka1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Kafka1Application {

	public static void main(String[] args) {
		SpringApplication.run(Kafka1Application.class, args);
	}

}

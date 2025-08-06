package com.example.graphql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {

	@Bean
	public RuntimeWiringConfigurer runtimeWiringConfigurer() {
		return builder -> builder
				.scalar(LocalDateTimeScalar.LOCAL_DATE_TIME)
				.build();
	}

}

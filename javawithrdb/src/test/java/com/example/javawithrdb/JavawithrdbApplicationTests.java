package com.example.javawithrdb;

import com.example.javawithrdb.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

@SpringBootTest
class JavawithrdbApplicationTests {

	@Autowired
	CustomJdbcRepository customJdbcRepository;

	@Autowired
	CustomJooqRepository customJooqRepository;

	@Autowired
	CustomJpaRepository customJpaRepository;

	@Autowired
	CustomMybatisRepository customMybatisRepository;

	@Autowired
	CustomQueryDslRepository customQueryDslRepository;

	@Test
	void insert() {
		StopWatch stopWatch = new StopWatch();

		stopWatch.start("JDBC insert");
		customJdbcRepository.insert();
		stopWatch.stop();


	}

}

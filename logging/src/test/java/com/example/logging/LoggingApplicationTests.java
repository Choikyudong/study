package com.example.logging;

import com.example.logging.entity.Users;
import com.example.logging.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoggingApplicationTests {

	@Autowired
	private UsersService usersService;

	@Rollback
	@BeforeEach
	@DisplayName("테스트용 사용자 생성")
	void setUp() {
		Users users = Users.builder()
				.userName("사용자1")
				.email("test@test.com")
				.passWord("mypassword9999")
				.phoneNumber("999-9999-9999")
				.build();
		assertNotNull(usersService.save(users));
	}

	@Test
	@DisplayName("로그인")
	void login() {
		Users users = Users.builder()
				.userName("사용자1")
				.passWord("mypassword9999")
				.build();
		assertNotNull(usersService.login(users));
	}

}

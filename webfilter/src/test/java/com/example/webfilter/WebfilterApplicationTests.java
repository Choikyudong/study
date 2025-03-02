package com.example.webfilter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WebfilterApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("접근 허용 테스트")
	void permitAll() throws Exception {
		mockMvc.perform(get("/permit-all"))
				.andExpect(status().isOk())
				.andExpect(content().string("Ok"));
	}

	@Test
	@DisplayName("회원 테스트")
	void memberOnly() throws Exception {
		mockMvc.perform(get("/member-only")
						.header("MEMBER", "MEMBER"))
				.andExpect(status().isOk())
				.andExpect(content().string("Welcome"));

		mockMvc.perform(get("/member-only")
						.header("MEMBER", ""))
				.andExpect(status().isForbidden())
				.andExpect(content().string("Who are you?"));

		mockMvc.perform(get("/member-only")
						.header("MEMBER", "USER"))
				.andExpect(status().isUnauthorized())
				.andExpect(content().string("Member Only"));
	}

	@Test
	@DisplayName("토큰 테스트")
	void tokenOnly() throws Exception {
		mockMvc.perform(get("/token-only")
						.header(HttpHeaders.AUTHORIZATION, "Bearer hahah"))
				.andExpect(status().isOk())
				.andExpect(content().string("Token~"));

		mockMvc.perform(get("/token-only")
						.header(HttpHeaders.AUTHORIZATION, "hahah"))
				.andExpect(status().isUnauthorized())
				.andExpect(content().string("Bearer not include"));

		mockMvc.perform(get("/token-only"))
				.andExpect(status().isForbidden())
				.andExpect(content().string("token not include"));
	}

	@Test
	@DisplayName("멤버와 토큰 테스트")
	void tokenMemberOnly() throws Exception {
		mockMvc.perform(get("/token-member-only")
						.header("MEMBER", "MEMBER")
						.header(HttpHeaders.AUTHORIZATION, "Bearer hahah"))
				.andExpect(status().isOk())
				.andExpect(content().string("TokenAndMember~"));

		mockMvc.perform(get("/token-member-only"))
				.andExpect(status().isForbidden())
				.andExpect(content().string("Who are you?"));

		mockMvc.perform(get("/token-member-only")
						.header("MEMBER", "MEMBER"))
				.andExpect(status().isForbidden())
				.andExpect(content().string("token not include"));

		mockMvc.perform(get("/token-member-only")
						.header(HttpHeaders.AUTHORIZATION, "Bearer hahah"))
				.andExpect(status().isForbidden())
				.andExpect(content().string("Who are you?"));
	}

}

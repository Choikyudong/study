package com.example.recordinjava;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RecordinjavaApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("회원 가입")
	public void save() throws Exception {
		mockMvc.perform(post("/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
      				"""
					{
						"name" : "tester",
						"age" : 1
					}
					"""
				)).andExpect(status().isCreated());
	}

}

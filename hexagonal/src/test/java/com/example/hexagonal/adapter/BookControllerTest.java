package com.example.hexagonal.adapter;

import com.example.hexagonal.domain.model.Book;
import com.example.hexagonal.domain.vo.Writer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void saveBook() throws Exception {
		// Given
		Book newBook = new Book(null, "Test", new Writer("Tester"));
		String bookJson = objectMapper.writeValueAsString(newBook);

		// When: 책 등록 요청
		mockMvc.perform(post("/api/book")
						.contentType(MediaType.APPLICATION_JSON)
						.content(bookJson))
				.andExpect(status().isOk())
				.andExpect(content().string("saved"));

		Long saveBookId = 1L;
		mockMvc.perform(get("/api/book/{bookId}", saveBookId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.bookName").value("Test")) // JSON 응답 내용 검증
				.andExpect(jsonPath("$.writer.name").value("Tester"));
	}


}

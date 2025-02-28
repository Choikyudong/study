package com.example.webandpaging;

import com.example.webandpaging.dto.BoardCursorRes;
import com.example.webandpaging.dto.BoardOffsetRes;
import com.example.webandpaging.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class WebandpagingApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BoardRepository boardRepository;

	private static final int PAGE_SIZE = 30;

	@Test
	void saveBoard() throws Exception {
		for (int i = 0; i < 50000; i++) {
			mockMvc.perform(post("/api")
					.contentType(MediaType.APPLICATION_JSON)
					.content("""
				{
				"author" : "tester",
				"subject" : "게시글" ,
				"content" : "본문"
				}
			""")).andExpect(status().isOk());
		}
		assertTrue(boardRepository.count() > 0);
	}

	@Test
	@DisplayName("Offset 기법 목록 호출")
	void getBoardsByOffset() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule()); // Jackson에서 LocalDateTime을 지원하지 않아 추가함
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		int pageNumber = 0;
		String uri = String.format("/api/offset?pageNumber=%d&pageSize=%d", pageNumber, PAGE_SIZE);
		while (true) {
			MvcResult result = mockMvc.perform(get(uri))
					.andExpect(status().isOk())
					.andReturn();
			String json = result.getResponse().getContentAsString();
			BoardOffsetRes res = mapper.readValue(json, BoardOffsetRes.class);
			if (!res.paging().hasNext()) {
				break;
			}
			pageNumber++;
			uri = String.format("/api/offset?pageNumber=%d&pageSize=%d", pageNumber, PAGE_SIZE);
		}
		stopWatch.stop();
		System.out.println(stopWatch.getTotalTime(TimeUnit.MINUTES));
	}

	@Test
	@DisplayName("Cursor 기법 목록 호출")
	void getBoardsByCursor() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		String uri = String.format("/api/cursor?cursor=%s&pageSize=%d", LocalDateTime.now(), PAGE_SIZE);
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		while (true) {
			MvcResult result = mockMvc.perform(get(uri))
					.andExpect(status().isOk())
					.andReturn();
			String json = result.getResponse().getContentAsString();
			BoardCursorRes res = mapper.readValue(json, BoardCursorRes.class);
			if (res.nextCursor() == null) {
				break;
			}
			uri = String.format("/api/cursor?cursor=%s&pageSize=%d", res.nextCursor(), PAGE_SIZE);
		}
		stopWatch.stop();
		System.out.println(stopWatch.getTotalTime(TimeUnit.MINUTES));
	}

}

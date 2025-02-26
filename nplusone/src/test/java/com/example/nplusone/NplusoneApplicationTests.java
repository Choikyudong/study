package com.example.nplusone;

import com.example.nplusone.entity.Comments;
import com.example.nplusone.entity.Posts;
import com.example.nplusone.service.PostsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class NplusoneApplicationTests {

	@Autowired
	private PostsService postsService;

	@Test
	@DisplayName("게시글 저장")
	void saveTest() {
		for (int i = 1; i <= 100; i++) {
			Posts posts = new Posts("tester", "테스트중");
			for (int j = 1; j <= 1000; j++) {
				posts.addComments(new Comments("tester", "댓글"));
			}
			postsService.save(posts);
		}
		System.out.println("카운트 : " + postsService.count());
		assertTrue(postsService.count() > 0);
	}

	@Test
	@DisplayName("N + 1 테스트")
	void commentTest() {
		// 페이징 처리
		Pageable pageable = PageRequest.of(0, 20);

		StopWatch lazyWatch = new StopWatch();
		lazyWatch.start();
		assertNotNull(postsService.findAllPostsLazy(pageable));
		lazyWatch.stop();
		System.out.println("lazy : " + lazyWatch.getTotalTime(TimeUnit.MILLISECONDS) + "ms");

		StopWatch fetchWatch = new StopWatch();
		fetchWatch.start();
		assertNotNull(postsService.findAllPostsFetch(pageable));
		fetchWatch.stop();
		System.out.println("fetch : " + fetchWatch.getTotalTime(TimeUnit.MILLISECONDS) + "ms");

		StopWatch subQuery = new StopWatch();
		subQuery.start();
		assertNotNull(postsService.findAllBySubQuery(pageable));
		subQuery.stop();
		System.out.println("subQuery : " + subQuery.getTotalTime(TimeUnit.MILLISECONDS) + "ms");

		StopWatch navtiveQuery = new StopWatch();
		navtiveQuery.start();
		assertNotNull(postsService.findAllByNavtiveQuery(pageable));
		navtiveQuery.stop();
		System.out.println("navtiveQuery : " + navtiveQuery.getTotalTime(TimeUnit.MILLISECONDS) + "ms");
	}


}

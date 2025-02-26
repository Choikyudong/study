package com.example.nplusone.service;

import com.example.nplusone.entity.Comments;
import com.example.nplusone.entity.Posts;
import com.example.nplusone.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostsService {

	private final PostsRepository postsRepository;

	@Transactional
	public void save(Posts posts) {
		postsRepository.save(posts);
	}

	public long count() {
		return postsRepository.count();
	}

	@Transactional(readOnly = true)
	public Page<Posts> findAllPostsLazy(Pageable pageable) {
		Page<Posts> posts = postsRepository.findAll(pageable);
		for (Posts post : posts) {
			for (Comments comment : post.getComments()) {
				String content = comment.getContent();
				// 추가할 기능
			}
		}
		return posts;
	}

	@Transactional(readOnly = true)
	public Page<Posts> findAllPostsFetch(Pageable pageable) {
		Page<Posts> posts = postsRepository.findAllWithComments(pageable);
		for (Posts post : posts) {
			for (Comments comment : post.getComments()) {
				String content = comment.getContent();
				// 추가할 기능
			}
		}
		return posts;
	}

	@Transactional(readOnly = true)
	public Page<Posts> findAllBySubQuery(Pageable pageable) {
		Page<Posts> posts = postsRepository.findAllBySubQuery(pageable);
		for (Posts post : posts) {
			for (Comments comment : post.getComments()) {
				String content = comment.getContent();
				// 추가할 기능
			}
		}
		return posts;
	}

	@Transactional(readOnly = true)
	public Page<Posts> findAllByNavtiveQuery(Pageable pageable) {
		Page<Object[]> posts = postsRepository.findAllByNativeQuery(pageable);
		Map<Long, Posts> postMap = new LinkedHashMap<>();
		for (Object[] row : posts.getContent()) {
			long pid = ((Number) row[0]).longValue();
			Posts post = postMap.computeIfAbsent(pid, id ->
				Posts.builder()
						.id(pid)
						.author((String) row[1])
						.content((String) row[2])
						.build()
			);
			if (Objects.nonNull(row[3])) {
				post.addComments(
						Comments.builder()
								.id(((Number) row[3]).longValue())
								.author((String) row[4])
								.content((String) row[5])
								.build()
				);
			}
		}
		return new PageImpl<>(new ArrayList<>(postMap.values()), posts.getPageable(), posts.getTotalElements());
	}


}
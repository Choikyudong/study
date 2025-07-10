package com.example.redis.adapter.out.redis;

import com.example.redis.application.port.PostCacheRepository;
import com.example.redis.domain.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisRepository implements PostCacheRepository {

	private final RedisTemplate<String, Post> redisTemplate;

	@Override
	public Post getPost(String postId) {
		return redisTemplate.opsForValue().get("test-post:" + postId);
	}

	@Override
	public boolean existsPost(String postId) {
		return redisTemplate.hasKey("test-post:" + postId);
	}

	@Override
	public void savePost(String postId, Post post) {
		redisTemplate.opsForValue().set("test-post:" + postId, post);
	}

}

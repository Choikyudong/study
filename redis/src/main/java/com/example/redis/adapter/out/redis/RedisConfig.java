package com.example.redis.adapter.out.redis;

import com.example.redis.domain.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

	@Bean
	public RedisTemplate<String, Post> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Post> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		redisTemplate.setKeySerializer(new StringRedisSerializer());

		Jackson2JsonRedisSerializer<Post> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Post.class);
		redisTemplate.setValueSerializer(jsonRedisSerializer);

		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(jsonRedisSerializer);

		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

}

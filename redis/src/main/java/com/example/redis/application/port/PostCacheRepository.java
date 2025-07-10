package com.example.redis.application.port;

import com.example.redis.domain.model.Post;

public interface PostCacheRepository {

	Post getPost(String postId);

	boolean existsPost(String postId);

	void savePost(String postId, Post post);

}

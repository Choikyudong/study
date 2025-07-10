package com.example.redis.application.port;

import com.example.redis.domain.model.Post;

public interface PostRepository {

	Post getPost(long postId);

	long savePost(Post post);

}

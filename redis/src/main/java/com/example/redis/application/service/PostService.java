package com.example.redis.application.service;

import com.example.redis.application.port.PostCacheRepository;
import com.example.redis.application.port.PostRepository;
import com.example.redis.domain.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final PostCacheRepository postCacheRepository;

	public Post getPost(long postId) {
		if (postCacheRepository.existsPost(String.valueOf(postId))){
			return postCacheRepository.getPost(String.valueOf(postId));
		}
		return postRepository.getPost(postId);
	}

	public URI savePost(Post save) {
		try {
			long postId = postRepository.savePost(save);
			save.setPostId(postId);
			postCacheRepository.savePost(String.valueOf(postId), save);
			return new URI("/post/" + postId);
		} catch (URISyntaxException u) {
			u.getStackTrace();
			return null;
		}
	}

}

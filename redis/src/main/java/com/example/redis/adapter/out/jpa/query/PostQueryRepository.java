package com.example.redis.adapter.out.jpa.query;

import com.example.redis.adapter.out.jpa.entity.PostEntity;
import com.example.redis.adapter.out.jpa.repository.PostJpaRepository;
import com.example.redis.application.port.PostRepository;
import com.example.redis.domain.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostQueryRepository implements PostRepository {

	private final PostJpaRepository postJpaRepository;

	@Override
	public Post getPost(long postId) {
		PostEntity postEntity = postJpaRepository.findById(postId)
				.orElseThrow(() -> new IllegalArgumentException("없음 ㅎ"));
		return new Post(
				postEntity.getPostId(),
				postEntity.getSubject(),
				postEntity.getContent()
		);
	}

	@Override
	public long savePost(Post post) {
		PostEntity postEntity = new PostEntity(
				post.getSubject(),
				post.getContent()
		);
		return postJpaRepository.save(postEntity).getPostId();
	}

}

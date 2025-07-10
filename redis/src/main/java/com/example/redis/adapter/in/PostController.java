package com.example.redis.adapter.in;

import com.example.redis.application.service.PostService;
import com.example.redis.domain.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping("/{postId}")
	public ResponseEntity<Post> getPost(@PathVariable long postId) {
		return ResponseEntity.ok(postService.getPost(postId));
	}

	@PostMapping
	public ResponseEntity<String> savePost(@RequestBody Post post) {
		return ResponseEntity.created(postService.savePost(post)).build();
	}

}

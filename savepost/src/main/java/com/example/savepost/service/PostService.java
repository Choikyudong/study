package com.example.savepost.service;

import com.example.savepost.domain.ContentType;
import com.example.savepost.dto.ContentSaveDto;
import com.example.savepost.dto.PostSaveDto;
import com.example.savepost.entity.ContentFile;
import com.example.savepost.entity.PostLink;
import com.example.savepost.entity.PostLob;
import com.example.savepost.repository.PostLinkRepository;
import com.example.savepost.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.*;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final PostLinkRepository postLinkRepository;

	public PostLink getPostLink(Long id) {
		return postLinkRepository.findById(id)
				.orElseThrow(IllegalArgumentException::new);
	}

	public PostLob getPostLob(Long id) {
		return postRepository.findById(id)
				.orElseThrow(IllegalArgumentException::new);
	}

	@Transactional
	public void savePostByLob(PostSaveDto saveReq) {
		postRepository.save(new PostLob(saveReq.subject(), saveReq.content()));
	}

	@Transactional
	public void savePostByLink(PostSaveDto saveReq) throws IOException {
		PostLink newPostLink = new PostLink(saveReq.subject(), saveReq.content());
		String contentBody = saveReq.content();

		// 본문 파일 치환
		Path uploadPath = Paths.get("/post/upload");
		Files.createDirectories(uploadPath);
		for (ContentSaveDto content : saveReq.contents()) {
			Path tempPath = Paths.get(content.filePath());
			Path filePath = uploadPath.resolve(tempPath.getFileName());
			Files.move(tempPath, filePath, StandardCopyOption.REPLACE_EXISTING);
			newPostLink.addConentFile(new ContentFile(newPostLink, content.filePath(), ContentType.IMAGE));

			// 간단한게 경로 수정
			contentBody = contentBody.replaceAll("status=temp", "status=upload");
		}
		newPostLink.updateContent(contentBody);
		postLinkRepository.save(newPostLink);
	}

}


package com.example.savepost.controller;

import com.example.savepost.dto.PostSaveDto;
import com.example.savepost.entity.PostLink;
import com.example.savepost.entity.PostLob;
import com.example.savepost.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping("/link/{id}")
	public ResponseEntity<PostLink> getPostLink(@PathVariable Long id) {
		return ResponseEntity.ok(postService.getPostLink(id));
	}

	@GetMapping("/lob/{id}")
	public ResponseEntity<PostLob> getPostLob(@PathVariable Long id) {
		return ResponseEntity.ok(postService.getPostLob(id));
	}

	@GetMapping("/imgeas")
	public ResponseEntity<Resource> serveImage(@RequestParam String status, @RequestParam String fileName) {
		try {
			Path filePath = Paths.get("/post/" + status).resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists() && resource.isReadable()) {
				return ResponseEntity.ok()
						.contentType(MediaType.IMAGE_JPEG) // 파일 타입에 따라 변경
						.body(resource);
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("/lob")
	public ResponseEntity<String> savePostByLob(@RequestBody PostSaveDto saveReq) {
		postService.savePostByLob(saveReq);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/link")
	public ResponseEntity<String> savePpostByLink(@RequestBody PostSaveDto saveReq) {
		try {
			postService.savePostByLink(saveReq);
			return ResponseEntity.ok().build();
		} catch (IOException i) {
			System.err.println("저장 실패 : " + i);
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping("/tempFile")
	public ResponseEntity<Map<String, String>> tempFile(@RequestParam("file") MultipartFile file) {
		Path dirPath = Paths.get("/post/temp");
		try {
			if (!Files.exists(dirPath)) {
				Files.createDirectories(dirPath);
			}
		} catch (IOException i) {
			System.err.println("디렉토리 생성 실패 : " + i);
		}

		String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
		Path filePath = dirPath.resolve(fileName);
		try {
			Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE_NEW);
		} catch (IOException i) {
			System.err.println("파일 저장 에러 발생 : " + i);
		}
		Map<String, String> response = new HashMap<>();
		response.put("fileName", fileName);
		response.put("filePath", filePath.toString());
		return ResponseEntity.ok(response);
	}

}

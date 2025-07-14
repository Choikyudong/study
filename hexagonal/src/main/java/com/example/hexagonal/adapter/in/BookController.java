package com.example.hexagonal.adapter.in;

import com.example.hexagonal.application.port.in.BookUseCase;
import com.example.hexagonal.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

	private final BookUseCase bookUseCase;

	@GetMapping("/{bookId}")
	public ResponseEntity<Book> getBook(@PathVariable Long bookId) {
		return ResponseEntity.ok(bookUseCase.getBook(bookId));
	}

	@PostMapping
	public ResponseEntity<String> saveBook(@RequestBody Book book) {
		bookUseCase.saveBook(book);
		return ResponseEntity.ok("saved");
	}

	@PutMapping("/lend/{bookId}")
	public ResponseEntity<Book> lendBook(@PathVariable Long bookId) {
		return ResponseEntity.ok(bookUseCase.lendBook(bookId));
	}

	@PutMapping("/restore/{bookId}")
	public ResponseEntity<Book> restoreBook(@PathVariable Long bookId) {
		return ResponseEntity.ok(bookUseCase.restoreBook(bookId));
	}

	@PutMapping("/return/{bookId}")
	public ResponseEntity<Book> returnBook(@PathVariable Long bookId) {
		return ResponseEntity.ok(bookUseCase.returnBook(bookId));
	}

	@DeleteMapping("/delete/{bookId}")
	public ResponseEntity<Book> deleteBook(@PathVariable Long bookId) {
		return ResponseEntity.ok(bookUseCase.deleteBook(bookId));
	}

}

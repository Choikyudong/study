package com.example.hexagonal.application.service;

import com.example.hexagonal.application.port.in.BookUseCase;
import com.example.hexagonal.application.port.out.BookRepository;
import com.example.hexagonal.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService implements BookUseCase {

	private final BookRepository bookRepository;

	@Override
	public Book getBook(Long bookId) {
		return bookRepository.getBook(bookId);
	}

	@Override
	public Book saveBook(Book book) {
		return bookRepository.saveBook(book);
	}

	@Override
	public Book lendBook(Long bookId) {
		Book book = bookRepository.getBook(bookId);
		book.lendBook();
		return saveBook(book);
	}

	@Override
	public Book deleteBook(Long bookId) {
		Book book = bookRepository.getBook(bookId);
		book.deleteBook();
		return saveBook(book);
	}

	@Override
	public Book restoreBook(Long bookId) {
		Book book = bookRepository.getBook(bookId);
		book.restoreBook();
		return saveBook(book);
	}

	@Override
	public Book returnBook(Long bookId) {
		Book book = bookRepository.getBook(bookId);
		book.returnBook();
		return saveBook(book);
	}

}

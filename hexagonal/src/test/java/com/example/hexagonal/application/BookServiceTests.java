package com.example.hexagonal.application;

import com.example.hexagonal.application.port.out.BookRepository;
import com.example.hexagonal.application.service.BookService;
import com.example.hexagonal.domain.model.Book;
import com.example.hexagonal.domain.vo.Writer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookService bookService;

	@Test
	void getBookTest() {
		// Given
		Long bookId = 1L;
		Book expectedBook = new Book(bookId, "Test", new Writer("tester"));
		when(bookRepository.getBook(bookId)).thenReturn(expectedBook);

		// when
		Book result = bookService.getBook(bookId);

		// then
		assertThat(result.getBookId()).isEqualTo(bookId);
		assertThat(result.getBookName()).isEqualTo("Test");
		verify(bookRepository).getBook(bookId);
	}

	@Test
	void saveBookTest() {
		// Given
		Book book = new Book(1L, "Test", new Writer("tester"));

		// when
		bookService.saveBook(book);

		// then
		verify(bookRepository).saveBook(book);
	}


}

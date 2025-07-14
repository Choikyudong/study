package com.example.hexagonal.domain;

import com.example.hexagonal.domain.model.Book;
import com.example.hexagonal.domain.vo.BookStatus;
import com.example.hexagonal.domain.vo.Writer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookTests {

	Book makeBookObject() {
		return new Book(
				1L,
				"Testbook",
				new Writer("Tester")
		);
	}

	@Test
	void registerBook() {
		// Given
		Long bookId = 10L;
		String bookName = "Test";
		Writer writer = new Writer("Tester");

		// when
		Book book = new Book(bookId, bookName, writer);

		// then
		assertThat(book.getBookId()).isEqualTo(bookId);
		assertThat(book.getBookName()).isEqualTo("Test");
	}

	@Test
	void lendBook() {
		// Given
		Book book = makeBookObject();

		// when
		book.lendBook();

		// then
		assertThat(book.getBookStatus()).isEqualTo(BookStatus.LENT);
	}

	@Test
	void returnBook() {
		// Given
		Book book = makeBookObject();
		book.lendBook();

		// when
		book.returnBook();

		// then
		assertThat(book.getBookStatus()).isEqualTo(BookStatus.AVAILABLE);
	}

	@Test
	void delBook() {
		// Given
		Book book = makeBookObject();

		// when
		book.deleteBook();

		// then
		assertThat(book.getBookStatus()).isEqualTo(BookStatus.NONE);
	}

	@Test
	void restoreBook() {
		// Given
		Book book = makeBookObject();
		book.deleteBook();

		// when
		book.restoreBook();

		// then
		assertThat(book.getBookStatus()).isEqualTo(BookStatus.AVAILABLE);
	}

	@Test
	void tryLendToLendBook() {
		// Given
		Book book = makeBookObject();

		// when
		book.lendBook();;

		// then
		assertThatThrownBy(book::lendBook)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("현재 대출이 불가능한 책입니다.");
	}

	@Test
	void tryLendToNoneBook() {
		// Given
		Book book = makeBookObject();

		// when
		book.deleteBook();;

		// then
		assertThatThrownBy(book::lendBook)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("현재 이용이 불가능한 책입니다.");
	}

}

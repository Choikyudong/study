package com.example.hexagonal.domain.model;

import com.example.hexagonal.domain.vo.BookStatus;
import com.example.hexagonal.domain.vo.Writer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(of = "bookId")
public class Book {

	private Long bookId;

	private String bookName;

	private Writer writer;

	private BookStatus bookStatus;

	protected Book() {
	}

	public Book(Long bookId, String bookName, Writer writer) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.writer = writer;
		this.bookStatus = BookStatus.AVAILABLE;
	}

	public Book(Long bookId, String bookName, Writer writer, BookStatus bookStatus) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.writer = writer;
		this.bookStatus = bookStatus;
	}

	public void lendBook() {
		if (this.bookStatus == BookStatus.NONE) {
			throw new IllegalArgumentException("현재 이용이 불가능한 책입니다.");
		}
		if (this.bookStatus == BookStatus.LENT) {
			throw new IllegalArgumentException("현재 대출이 불가능한 책입니다.");
		}
		this.bookStatus = BookStatus.LENT;
	}

	public void returnBook() {
		if (this.bookStatus == BookStatus.AVAILABLE || this.bookStatus == BookStatus.NONE) {
			throw new IllegalArgumentException("반납할 수 없는 상태의 책입니다.");
		}
		this.bookStatus = BookStatus.AVAILABLE;
	}

	public void restoreBook() {
		this.bookStatus = BookStatus.AVAILABLE;
	}

	public void deleteBook() {
		this.bookStatus = BookStatus.NONE;
	}

}

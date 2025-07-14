package com.example.hexagonal.application.port.in;

import com.example.hexagonal.domain.model.Book;

public interface BookUseCase {

	Book getBook(Long bookId);

	Book saveBook(Book book);

	Book lendBook(Long bookId);

	Book deleteBook(Long bookId);

	Book restoreBook(Long bookId);

	Book returnBook(Long bookId);

}

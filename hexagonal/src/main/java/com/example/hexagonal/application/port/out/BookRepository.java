package com.example.hexagonal.application.port.out;

import com.example.hexagonal.domain.model.Book;

public interface BookRepository {

	Book getBook(Long bookId);

	Book saveBook(Book book);

}

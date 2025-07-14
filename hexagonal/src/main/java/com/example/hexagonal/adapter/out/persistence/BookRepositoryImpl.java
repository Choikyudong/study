package com.example.hexagonal.adapter.out.persistence;

import com.example.hexagonal.adapter.out.entity.BookEntity;
import com.example.hexagonal.adapter.out.entity.vo.WriterVO;
import com.example.hexagonal.adapter.out.repository.BookJpaRepository;
import com.example.hexagonal.application.port.out.BookRepository;
import com.example.hexagonal.domain.model.Book;
import com.example.hexagonal.domain.vo.Writer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

	private final BookJpaRepository bookJpaRepository;

	@Override
	public Book getBook(Long bookId) {
		BookEntity bookEntity = bookJpaRepository.findById(bookId)
				.orElseThrow(() -> new IllegalArgumentException("에러"));
		return new Book(
				bookEntity.getBookId(),
				bookEntity.getBookName(),
				new Writer(
						bookEntity.getWriter().getName()
				),
				bookEntity.getBookStatus()
		);
	}

	@Override
	public Book saveBook(Book book) {
		BookEntity bookEntity = new BookEntity(
				book.getBookId(),
				book.getBookName(),
				new WriterVO(
						book.getWriter().name()
				),
				book.getBookStatus()
		);
		bookJpaRepository.save(bookEntity);
		return new Book(
				bookEntity.getBookId(),
				bookEntity.getBookName(),
				new Writer(
						bookEntity.getWriter().getName()
				),
				bookEntity.getBookStatus()
		);
	}

}

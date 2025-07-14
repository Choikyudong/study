package com.example.hexagonal.adapter.out.entity;

import com.example.hexagonal.adapter.out.entity.vo.WriterVO;
import com.example.hexagonal.domain.vo.BookStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bookId;

	private String bookName;

	@Embedded
	private WriterVO writer;

	@Enumerated(EnumType.STRING)
	private BookStatus bookStatus;

	public BookEntity(Long bookId, String bookName, WriterVO writer, BookStatus bookStatus) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.writer = writer;
		this.bookStatus = bookStatus;
	}

}

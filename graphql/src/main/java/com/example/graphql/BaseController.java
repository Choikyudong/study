package com.example.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class BaseController {

	private static final Map<String, Author> authorMap = new HashMap<>();
	private static final Map<String, Book> bookMap = new HashMap<>();

	@QueryMapping
	public String hello() {
		return "Hello GraphQL!";
	}

	@QueryMapping
	public Author findAuthor(@Argument String id) {
		return authorMap.get(id);
	}

	@MutationMapping
	public Author registerAuthor(@Argument RegisterAuthorInput input) {
		Author newAuthor = Author.from(input);
		authorMap.put(newAuthor.id(), newAuthor);
		return newAuthor;
	}

	@MutationMapping
	public Book createBook(@Argument CreateBookInput input) {
		Author author = authorMap.get(input.authorId());
		if (author == null) {
			throw new IllegalArgumentException("Author not found with id: " + input.authorId());
		}

		String bookId = UUID.randomUUID().toString();
		Book book = new Book(bookId, input.title(), author);

		author.books().add(book);
		bookMap.put(bookId, book);

		return book;
	}

	@MutationMapping
	public Boolean updateBookTitle(@Argument String id, String title) {
		Book book = Optional.of(bookMap.get(id))
				.orElseThrow(() -> new IllegalArgumentException("Book not found"));

		book.setTitle(title);
		bookMap.put(id, book);
		return true;
	}

	@QueryMapping
	public Book getBook(@Argument String id) {
		return bookMap.get(id);
	}

	@QueryMapping
	public List<Book> getBooks() {
		return new ArrayList<>(bookMap.values());
	}

}

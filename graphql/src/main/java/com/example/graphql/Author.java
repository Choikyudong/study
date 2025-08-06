package com.example.graphql;

import java.util.ArrayList;
import java.util.List;

public record Author(String id, String name, List<Book> books) {
	public static Author from(RegisterAuthorInput input) {
		return new Author(
				input.id(), input.name(), new ArrayList<>()
		);
	}
}

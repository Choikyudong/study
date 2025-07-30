package com.example.javawithrdb.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomJooqRepository {

	private final DSLContext dslContext;

	public Record findById(Long id) {
		return dslContext.select()
				.from("user")
				.where(DSL.field("id").eq(id))
				.fetchOne();
	}


}

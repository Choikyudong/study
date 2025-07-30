package com.example.javawithrdb.repository;

import com.example.javawithrdb.entity.QUser;
import com.example.javawithrdb.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomQueryDslRepository {

	private final JPAQueryFactory queryFactory;

	public User findById(Long id) {
		QUser user = QUser.user;
		return queryFactory.selectFrom(user)
				.where(user.id.eq(id))
				.fetchOne();
	}

}

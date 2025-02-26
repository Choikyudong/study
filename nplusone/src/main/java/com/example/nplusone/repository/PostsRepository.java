package com.example.nplusone.repository;

import com.example.nplusone.entity.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {

	@Query("SELECT p FROM Posts p JOIN FETCH p.comments")
	Page<Posts> findAllWithComments(Pageable pageable);

	@Query("SELECT p FROM Posts p WHERE p.id IN (SELECT p2.id FROM Posts p2)")
	Page<Posts> findAllBySubQuery(Pageable pageable);

	// Page 객체에서 레코드 수 파악을 위해 countQuery를 작성
	@Query(value =
			"SELECT p.id, p.author, p.content, c.id AS cid, c.author AS cauthor, c.content AS ccontent " +
			"FROM Posts p LEFT JOIN Comments c ON p.id = c.posts_id " +
			"WHERE p.id IN (SELECT id FROM Posts ORDER BY id LIMIT :#{#pageable.pageSize} OFFSET :#{#pageable.offset})",
			countQuery = "SELECT COUNT(id) FROM Posts",
			nativeQuery = true)
	Page<Object[]> findAllByNativeQuery(Pageable pageable);

}

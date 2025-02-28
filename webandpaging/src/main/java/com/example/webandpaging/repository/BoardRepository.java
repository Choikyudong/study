package com.example.webandpaging.repository;

import com.example.webandpaging.entity.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

	Slice<Board> findByLastModifiedAtLessThanOrderByLastModifiedAtDesc(LocalDateTime lastModifiedAt, Pageable pageable);

}

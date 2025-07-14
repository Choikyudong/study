package com.example.hexagonal.adapter.out.repository;

import com.example.hexagonal.adapter.out.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {
}

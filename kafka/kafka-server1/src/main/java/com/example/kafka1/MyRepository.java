package com.example.kafka1;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyRepository extends JpaRepository<MyEntity, Long> {
}

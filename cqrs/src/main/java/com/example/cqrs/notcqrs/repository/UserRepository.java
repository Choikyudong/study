package com.example.cqrs.notcqrs.repository;

import com.example.cqrs.notcqrs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

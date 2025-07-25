package com.example.cqrs.cqrsv1.command.repository;

import com.example.cqrs.notcqrs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCommandRepository extends JpaRepository<User, Long> {
}

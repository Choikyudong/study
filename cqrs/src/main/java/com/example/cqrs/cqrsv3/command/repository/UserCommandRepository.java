package com.example.cqrs.cqrsv3.command.repository;

import com.example.cqrs.notcqrs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Deprecated
public interface UserCommandRepository extends JpaRepository<User, Long> {
}

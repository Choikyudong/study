package com.example.javawithrdb.repository;

import com.example.javawithrdb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomJpaRepository extends JpaRepository<User, Long> {
}

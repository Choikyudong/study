package com.example.logging.repository;

import com.example.logging.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByUserName(String userName);

}

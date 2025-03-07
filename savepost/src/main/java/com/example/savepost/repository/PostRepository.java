package com.example.savepost.repository;

import com.example.savepost.entity.PostLob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostLob, Long> {
}

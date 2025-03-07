package com.example.savepost.repository;

import com.example.savepost.entity.PostLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLinkRepository extends JpaRepository<PostLink, Long> {
}

package com.example.redis.adapter.out.jpa.repository;

import com.example.redis.adapter.out.jpa.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {
}

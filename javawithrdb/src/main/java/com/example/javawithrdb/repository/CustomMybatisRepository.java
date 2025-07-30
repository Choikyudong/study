package com.example.javawithrdb.repository;

import com.example.javawithrdb.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomMybatisRepository {
	User findById(Long id);
}

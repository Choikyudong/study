package com.example.recordinjava.service;

import com.example.recordinjava.entity.Users;
import com.example.recordinjava.record.UserSaveReq;
import com.example.recordinjava.record.UserSaveRes;
import com.example.recordinjava.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

	private final UsersRepository usersRepository;

	public UserSaveRes save(UserSaveReq saveReq) {
		Users newUsers = new Users(
				saveReq.name(),
				saveReq.age()
		);
		return UserSaveRes.convert(usersRepository.save(newUsers));
	}

}

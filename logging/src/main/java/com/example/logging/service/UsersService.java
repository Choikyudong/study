package com.example.logging.service;

import com.example.logging.entity.Users;
import com.example.logging.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {

	private final UsersRepository usersRepository;

	public Users save(Users users) {
		Users newUsers =  usersRepository.save(users);
		log.info("신규 회원 {} 가입 완료", users.getUserName());
		return newUsers;
	}

	public Users login(Users reqeust) {
		Optional<Users> usersResult = usersRepository.findByUserName(reqeust.getUserName());
		if (usersResult.isEmpty()) {
			log.error("사용자 {}가 로그인에 실패했습니다.", reqeust.getUserName());
			throw new IllegalArgumentException("");
		}

		Users users = usersResult.get();
		if (!users.getPassWord().equals(reqeust.getPassWord())) {
			log.info("사용자의 비밀번호가 일치하지 않습니다. 저장된 비밀번호 : {}", users.getPassWord());
		}

		// 사용자 이메일 발송이 실패할 경우 정보와 예외를 입력한다.
		try {
			// 이메일 발송..
		} catch (Exception e) {
			// 추가로 컨텍스트를 담아서 문제 분석에 도움이 되도록 하자.
			log.error("이메일 발송 실패 - 사용자: {}, 이메일: {}", users.getUserName(), users.getEmail(), e);
		}
		return users;
	}

}

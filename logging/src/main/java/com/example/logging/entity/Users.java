package com.example.logging.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String userName;

	private String email;

	private String passWord;

	private String phoneNumber;

	@Builder
	public Users(String userName, String email, String passWord, String phoneNumber) {
		this.userName = userName;
		this.email = email;
		this.passWord = passWord;
		this.phoneNumber = phoneNumber;
	}

}

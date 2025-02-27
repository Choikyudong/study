package com.example.recordinjava.record;

import com.example.recordinjava.entity.Users;

import java.util.Objects;

public record UserSaveRes(String name, int age, String msg) {
	public static UserSaveRes convert(Users users) {
		Objects.requireNonNull(users, "Users can`t be null");
		String msg = "Welcome " +  users.getName();
		return new UserSaveRes(users.getName(), users.getAge(), msg);
	}
}

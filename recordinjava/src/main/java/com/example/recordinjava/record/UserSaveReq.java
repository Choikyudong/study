package com.example.recordinjava.record;

import java.util.Objects;

public record UserSaveReq(String name, int age) {
	public UserSaveReq {
		Objects.requireNonNull(name, "Name can`t be null");
		if (age < 0) {
			throw new IllegalArgumentException("Age must be non-negative");
		}
	}
}

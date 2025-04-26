package com.example.mvc;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyEntity {

	@Id
	@GeneratedValue
	private long id;

	private String text;

	public MyEntity(String text) {
		this.text = text;
	}

}

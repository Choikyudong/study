package com.example.kafka2;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyEntity {

	@Id
	private Long id;

	public MyEntity(Long id) {
		this.id = id;
	}

}

package com.example.eda2;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyEntity {

	private Long eventId; // 이벤트 고유 식별자

	@Id
	private Long id;

	public MyEntity(Long id) {
		this.id = id;
	}

}

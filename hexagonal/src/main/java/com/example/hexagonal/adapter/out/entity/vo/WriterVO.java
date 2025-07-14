package com.example.hexagonal.adapter.out.entity.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class WriterVO {

	private String name;

	protected WriterVO() {
	}

	public WriterVO(String name) {
		this.name = name;
	}

}

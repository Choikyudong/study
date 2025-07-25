package com.example.cqrs.cqrsv1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "Contact")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contact {

	@Id
	private Long contactId;

	@ManyToOne
	private User user;

	private String type;

	private String detail;

	public Contact(String type, String detail) {
		this.type = type;
		this.detail = detail;
	}

}

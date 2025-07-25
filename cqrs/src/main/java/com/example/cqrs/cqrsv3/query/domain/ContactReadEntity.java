package com.example.cqrs.cqrsv3.query.domain;

import com.example.cqrs.cqrsv2.query.domain.UserReadEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "CONTACT_READ_VIEW")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContactReadEntity {

	@Id
	private Long contactId;

	@ManyToOne
	private com.example.cqrs.cqrsv2.query.domain.UserReadEntity user;

	private String type;

	private String detail;

	public ContactReadEntity(String type, String detail) {
		this.type = type;
		this.detail = detail;
	}

	public void setUser(UserReadEntity user) {
		this.user = user;
	}

}

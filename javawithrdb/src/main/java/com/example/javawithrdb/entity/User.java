package com.example.javawithrdb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

	@Id
	@GeneratedValue
	private long id;

	@OneToMany(
			mappedBy = "user",
			fetch = FetchType.LAZY
	)
	private List<Order> orders = new ArrayList<>();

	private String name;

	@CreatedDate
	private LocalDateTime createdAt;

	public User(String name) {
		this.name = name;
	}

}

package com.example.concurrencycontrol.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.cert.CertificateExpiredException;

@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private int stock;

	@Version // 낙관적 잠금을 위한 필드, JPA에서 관리함.
	private Long version;
	public Product(Long id, String name, int stock) {
		this.id = id;
		this.name = name;
		this.stock = stock;
	}

	public void decreaseStock(int quantity) {
		if (this.stock < quantity) {
			throw new IllegalArgumentException("재고가 부족합니다.");
		}
		this.stock -= quantity;
	}
	public void increaseStock(int quantity) {
		this.stock += quantity;
	}

}

package com.example.javawithrdb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders_item")
@NoArgsConstructor
@AllArgsConstructor
public class OrdersItem {

	@Id
	@GeneratedValue
	private long id;

	private String name;

	private long price;

}

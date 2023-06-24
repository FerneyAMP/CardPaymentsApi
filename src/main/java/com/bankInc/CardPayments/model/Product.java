package com.bankInc.CardPayments.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
	@Id
	private Integer id;
	
	@Column(name = "name")
	private String name;

}

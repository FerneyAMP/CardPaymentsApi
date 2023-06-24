package com.bankInc.CardPayments.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "card")
public class Card {
	@Id
	@SequenceGenerator(name="card_sq", sequenceName="card_sq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="card_sq")
	private Integer id;
	
	@Column(name = "card_number")
	private Long number;
	
	@Column(name = "card_holder_name")
	private String cardHolderName;
	
	@Column(name = "due_date")
	private Date dueDate;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "balance")
	private BigDecimal balance;
			
	@Column(name = "product_id")
	private Integer productId;

	public Card() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
}

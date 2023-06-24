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
@Table(name = "transaction")
public class Transaction {
	
	@Id
	@SequenceGenerator(name="transaction_sq", sequenceName="transaction_sq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="transaction_sq")
	private Integer id;
	
	@Column(name = "transaction_value")
	private BigDecimal value;
	
	@Column(name = "debtor_card_number")
	private Long debtorCardNumber;
		
	@Column(name = "date")
	private Date date;
	
	@Column(name = "status")
	private String status;

	public Transaction() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Long getDebtorCardNumber() {
		return debtorCardNumber;
	}

	public void setDebtorCardNumber(Long debtorCardNumber) {
		this.debtorCardNumber = debtorCardNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}

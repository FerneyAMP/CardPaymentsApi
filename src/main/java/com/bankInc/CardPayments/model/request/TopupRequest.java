package com.bankInc.CardPayments.model.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Digits;


public class TopupRequest {
	@NotNull(message = "Falta el numero de tarjeta.")
	@Digits(fraction = 0, integer = 16, message = "Número de tarjeta invalido.")
	private Long cardId;
	
	@NotNull(message = "Falta el valor a recargar.")
	@Min(value = 0, message = "Valor a recargar no válido.")
	private BigDecimal balance;
	
	public TopupRequest() {}
	
	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
}

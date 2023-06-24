package com.bankInc.CardPayments.model.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

public class AnulationRequest {
	
	@NotNull(message = "Falta el numero de tarjeta.")
	@Digits(fraction = 0, integer = 16, message = "Número de tarjeta invalido.")
	private Long cardId;
	
	@NotNull(message = "Falta el identificador de la transacción.")
	private Integer transactionId;
	
	public AnulationRequest() {}
	
	public Long getCardId() {
		return cardId;
	}
	
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	
	public Integer getTransactionId() {
		return transactionId;
	}
	
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	
}

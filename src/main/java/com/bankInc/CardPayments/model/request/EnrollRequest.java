package com.bankInc.CardPayments.model.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

public class EnrollRequest {
	
	@NotNull(message = "Falta el numero de tarjeta")
	@Digits(fraction = 0, integer = 10, message = "Número de tarjeta no válido")
	private Long cardId;
	
	public EnrollRequest() {}
	
	public EnrollRequest(Long cardId) {
		this.cardId = cardId;
	}

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
}

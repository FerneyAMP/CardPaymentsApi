package com.bankInc.CardPayments.model.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PurchaseRequest {
	@NotNull(message = "Falta el numero de tarjeta")
	@Digits(fraction = 0, integer = 16, message = "Número de tarjeta no válido")
	private Long cardId;
	
	@NotNull(message = "Falta el valor de la compra")
	@Min(value = 0, message = "Valor de la compra no válido")
	private BigDecimal price;
	
	public PurchaseRequest() {}
	
	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}

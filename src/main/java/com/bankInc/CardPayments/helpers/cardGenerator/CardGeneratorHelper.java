package com.bankInc.CardPayments.helpers.cardGenerator;

import java.math.BigDecimal;

import com.bankInc.CardPayments.model.Card;

public interface CardGeneratorHelper {
	
	public Card generateCard(Integer ProductId, Integer cardYears, String holderName, String status,
			BigDecimal initialBalance);
}

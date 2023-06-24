package com.bankInc.CardPayments.helpers.cardGenerator;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankInc.CardPayments.helpers.cardNumberGenerator.CardNumberGeneratorHelper;
import com.bankInc.CardPayments.helpers.dueDateCalculator.CardDueDateCalculatorHelper;
import com.bankInc.CardPayments.model.Card;

@Service
public class ClientCardGenerator implements CardGeneratorHelper {

	private CardNumberGeneratorHelper numberGenerator;
	private CardDueDateCalculatorHelper dueDateCalculator;
	
	@Autowired
	public ClientCardGenerator(CardNumberGeneratorHelper numberGenerator,
			CardDueDateCalculatorHelper dueDateCalculator) {
		super();
		this.numberGenerator = numberGenerator;
		this.dueDateCalculator = dueDateCalculator;
	}



	@Override
	public Card generateCard(Integer ProductId, Integer cardYears, String holderName, String status, BigDecimal initialBalance) {
		
		Card newCard = new Card();
		newCard.setNumber(numberGenerator.generateCardNumber(ProductId));
		newCard.setDueDate(dueDateCalculator.calculateDueDate(cardYears));
		newCard.setCardHolderName(holderName);
		newCard.setStatus(status);
		newCard.setBalance(initialBalance);
		newCard.setProductId(ProductId);
		
		return newCard;
	}

}

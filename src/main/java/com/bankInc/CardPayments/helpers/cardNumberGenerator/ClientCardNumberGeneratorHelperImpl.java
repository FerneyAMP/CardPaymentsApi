package com.bankInc.CardPayments.helpers.cardNumberGenerator;

import org.springframework.stereotype.Service;

@Service
public class ClientCardNumberGeneratorHelperImpl implements CardNumberGeneratorHelper {

	@Override
	public Long generateCardNumber(Integer productId) {
		
		String firstRandomDigit = String.valueOf((int)Math.floor(Math.random() * 10));
		String remainderRandomDigits = String.valueOf((long) Math.floor(Math.random() * 9000000000L) + 1000000000L).substring(1);
		String cardNumberText = String.join("",String.valueOf(productId), firstRandomDigit, remainderRandomDigits);
		return Long.valueOf(cardNumberText);
		
	}

}

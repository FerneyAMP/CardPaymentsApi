package com.bankInc.CardPayments.service.exceptions;

import org.springframework.beans.factory.annotation.Value;

public class NotActiveCardException extends Exception{

	public NotActiveCardException(Long cardNumber) {
		
		super("The card with number " + cardNumber + " is not active");
	}
}

package com.bankInc.CardPayments.service.exceptions;

public class InsufficientFundsException extends Exception {
	public InsufficientFundsException(Long cardId) {
		super("The card with number "+cardId+" has no funds");
	}

}

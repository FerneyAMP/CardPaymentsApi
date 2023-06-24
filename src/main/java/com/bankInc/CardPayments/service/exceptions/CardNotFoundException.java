package com.bankInc.CardPayments.service.exceptions;

public class CardNotFoundException extends Exception{
	public CardNotFoundException(Long cardNumber) {
		super("Card with number "+cardNumber+" not found");
	}
}

package com.bankInc.CardPayments.service.exceptions;

public class NotValidRefundException extends Exception{
	public NotValidRefundException() {
		super("The refund is not valid");
	}
}

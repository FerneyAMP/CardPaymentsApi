package com.bankInc.CardPayments.service.exceptions;

public class TransactionNotFoundException extends Exception {
	public TransactionNotFoundException(Integer transactionId) {
		super("Transaction with id " + transactionId + " not found");
	}
}

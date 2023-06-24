package com.bankInc.CardPayments.service.exceptions;

public class NotExistentProductException extends Exception{
	public NotExistentProductException(Integer ProductId) {
		super("The product with id "+ProductId+" does no exist");
	}

}

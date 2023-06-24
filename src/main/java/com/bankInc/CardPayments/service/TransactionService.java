package com.bankInc.CardPayments.service;

import java.util.Optional;

import com.bankInc.CardPayments.model.Transaction;
import com.bankInc.CardPayments.model.request.AnulationRequest;
import com.bankInc.CardPayments.model.request.PurchaseRequest;
import com.bankInc.CardPayments.service.exceptions.CardNotFoundException;
import com.bankInc.CardPayments.service.exceptions.InsufficientFundsException;
import com.bankInc.CardPayments.service.exceptions.NotActiveCardException;
import com.bankInc.CardPayments.service.exceptions.NotValidRefundException;
import com.bankInc.CardPayments.service.exceptions.TransactionNotFoundException;

public interface TransactionService {
	
	public Integer makePurchase(PurchaseRequest request) throws CardNotFoundException, InsufficientFundsException, NotActiveCardException;
	public Transaction getTransaction(Integer transactionId) throws TransactionNotFoundException;
	public void cancelTransaction(AnulationRequest request) throws NotValidRefundException, TransactionNotFoundException;
	
}

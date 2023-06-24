package com.bankInc.CardPayments.service;

import java.math.BigDecimal;

import com.bankInc.CardPayments.model.request.EnrollRequest;
import com.bankInc.CardPayments.model.request.TopupRequest;
import com.bankInc.CardPayments.service.exceptions.CardNotFoundException;
import com.bankInc.CardPayments.service.exceptions.NotActiveCardException;
import com.bankInc.CardPayments.service.exceptions.NotExistentProductException;

public interface CardService {
	public Long generateCardNumber(Integer ProductId) throws NotExistentProductException;
	public void enrollCard(EnrollRequest request) throws CardNotFoundException;
	public void blockCard(Long cardId) throws CardNotFoundException;
	public void topUpCard(TopupRequest request) throws NotActiveCardException, CardNotFoundException;
	public BigDecimal getBalance(Long cardId) throws CardNotFoundException;
}

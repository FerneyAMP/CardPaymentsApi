package com.bankInc.CardPayments.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankInc.CardPayments.model.Card;
import com.bankInc.CardPayments.model.Transaction;
import com.bankInc.CardPayments.model.request.AnulationRequest;
import com.bankInc.CardPayments.model.request.PurchaseRequest;
import com.bankInc.CardPayments.repository.CardDao;
import com.bankInc.CardPayments.repository.TransactionDao;
import com.bankInc.CardPayments.service.exceptions.CardNotFoundException;
import com.bankInc.CardPayments.service.exceptions.InsufficientFundsException;
import com.bankInc.CardPayments.service.exceptions.NotActiveCardException;
import com.bankInc.CardPayments.service.exceptions.NotValidRefundException;
import com.bankInc.CardPayments.service.exceptions.TransactionNotFoundException;

@Service
public class TransactionServiceImpl implements TransactionService {

	private TransactionDao transactionDao;
	private CardDao cardDao;
	private static final String ACTIVE_STATUS = "ACTIVE";
	private static final String VALIDATED_STATUS = "VALIDATED";
	private static final String ANULATED_STATUS = "ANULATED";
	
	
	@Autowired
	public TransactionServiceImpl(TransactionDao transactionDao, CardDao cardDao) {
		this.transactionDao = transactionDao;
		this.cardDao = cardDao;
	}

	@Override
	@Transactional
	public Integer makePurchase(PurchaseRequest request) throws CardNotFoundException, InsufficientFundsException, NotActiveCardException {
		Card card = cardDao.findByNumber(request.getCardId()).orElseThrow(() -> new CardNotFoundException(request.getCardId()));
		if(isNotActive(card)) {
			throw new NotActiveCardException(card.getNumber());
		}
		BigDecimal cardBalance = cardDao.GetBalance(request.getCardId());
		BigDecimal newBalance = cardBalance.subtract(request.getPrice());
		if(hasSuficcientFunds(newBalance)) {
			Transaction transaction = saveTransaction(request.getPrice(), request.getCardId(), new Date());
			cardDao.saveCardBalance(newBalance, request.getCardId());
			return transaction.getId();
		}else {
			throw new InsufficientFundsException(request.getCardId());
		}
		
	}

	@Override
	public Transaction getTransaction(Integer transactionId) throws TransactionNotFoundException {
		return transactionDao.findById(transactionId).orElseThrow(() -> new TransactionNotFoundException(transactionId));
	}

	@Override
	@Transactional
	public void cancelTransaction(AnulationRequest request) throws NotValidRefundException, TransactionNotFoundException {
		Transaction transactionToCancel = getTransaction(request.getTransactionId());
		if(isValidRequest(request,transactionToCancel)) {
			transactionDao.saveTransactionStatus(ANULATED_STATUS, request.getTransactionId());
			BigDecimal currentBalance = cardDao.GetBalance(request.getCardId());
			BigDecimal newBalance = currentBalance.add(transactionToCancel.getValue());
			cardDao.saveCardBalance(newBalance,request.getCardId());
		}else {
			throw new NotValidRefundException();
		}
		
		
	}
		
	private boolean hasSuficcientFunds(BigDecimal balance) {
		return balance.compareTo(BigDecimal.ZERO) >= 0;
	}
	
	private boolean isNotActive(Card c) {
		return !ACTIVE_STATUS.equals(c.getStatus());
	}
	
	private Transaction saveTransaction(BigDecimal value, Long cardId, Date date) {
		Transaction t = new Transaction();
		t.setValue(value);
		t.setDebtorCardNumber(cardId);
		t.setDate(date);
		t.setStatus(VALIDATED_STATUS);
		return transactionDao.save(t);
	}
	
	private boolean isValidRequest(AnulationRequest request, Transaction transactionToCancel) {
		return request.getCardId().equals(transactionToCancel.getDebtorCardNumber());
	}
	
}

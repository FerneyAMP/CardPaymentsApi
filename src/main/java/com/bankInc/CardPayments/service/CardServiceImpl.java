package com.bankInc.CardPayments.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankInc.CardPayments.helpers.cardGenerator.CardGeneratorHelper;
import com.bankInc.CardPayments.model.Card;
import com.bankInc.CardPayments.model.request.EnrollRequest;
import com.bankInc.CardPayments.model.request.TopupRequest;
import com.bankInc.CardPayments.repository.CardDao;
import com.bankInc.CardPayments.repository.ProductDao;
import com.bankInc.CardPayments.service.exceptions.CardNotFoundException;
import com.bankInc.CardPayments.service.exceptions.NotActiveCardException;
import com.bankInc.CardPayments.service.exceptions.NotExistentProductException;

@Service
public class CardServiceImpl implements CardService {

	public static final String INITIAL_BALANCE = "0";
	public static final String ACTIVE_STATUS = "ACTIVE";
	public static final String INACTIVE_STATUS = "INACTIVE";
	public static final String BLOCKED_STATUS = "BLOCKED";
	public static final Integer DEFAULT_CARD_YEARS = 3;
	
	private CardDao cardDao;
	private ProductDao productDao;
	private CardGeneratorHelper cardGeneratorHelper;
	
	@Autowired
	public CardServiceImpl(CardDao cardDao, ProductDao productDao, CardGeneratorHelper cardGeneratorHelper) {
		this.cardDao = cardDao;
		this.productDao = productDao;
		this.cardGeneratorHelper = cardGeneratorHelper;
	}

	@Override
	public Long generateCardNumber(Integer ProductId) throws NotExistentProductException {
		if(productExists(ProductId)) {
			Card newCard = cardGeneratorHelper.generateCard(ProductId, DEFAULT_CARD_YEARS, "A TEST NAME", INACTIVE_STATUS, new BigDecimal(INITIAL_BALANCE));
			return cardDao.save(newCard).getNumber();
		}else {
			throw new NotExistentProductException(ProductId);
		}
	}

	@Override
	@Transactional
	public void enrollCard(EnrollRequest request) throws CardNotFoundException {
		int rowsUpdated = cardDao.saveCardStatus(ACTIVE_STATUS, request.getCardId());
		if(rowsUpdated == 0) {
			throw new CardNotFoundException(request.getCardId());
		}
	}

	@Override
	@Transactional
	public void blockCard(Long cardId) throws CardNotFoundException {
		int rowsUpdated = cardDao.saveCardStatus(BLOCKED_STATUS, cardId);
		if(rowsUpdated == 0) {
			throw new CardNotFoundException(cardId);
		}
	}

	@Override
	@Transactional
	public void topUpCard(TopupRequest request) throws NotActiveCardException, CardNotFoundException {
		Card card = cardDao.findByNumber(request.getCardId()).orElseThrow(() -> new CardNotFoundException(request.getCardId()));
		if(isCardActive(card)) {
			BigDecimal newBalance = card.getBalance().add(request.getBalance());
			cardDao.saveCardBalance(newBalance,request.getCardId());
		}else {
		  throw new NotActiveCardException(card.getNumber());
		}
	}

	@Override
	public BigDecimal getBalance(Long cardId) throws CardNotFoundException {
		return Optional.ofNullable(cardDao.GetBalance(cardId)).orElseThrow(() -> new CardNotFoundException(cardId));
	}
	
	
	private boolean isCardActive(Card card){
		return ACTIVE_STATUS.equals(card.getStatus());
	}
	
	private boolean productExists(Integer ProductId) {
		return productDao.existsById(ProductId);
	}
	
}

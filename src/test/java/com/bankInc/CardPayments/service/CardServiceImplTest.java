package com.bankInc.CardPayments.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import com.bankInc.CardPayments.model.Card;
import com.bankInc.CardPayments.model.request.EnrollRequest;
import com.bankInc.CardPayments.model.request.TopupRequest;
import com.bankInc.CardPayments.repository.CardDao;
import com.bankInc.CardPayments.service.exceptions.CardNotFoundException;
import com.bankInc.CardPayments.service.exceptions.NotActiveCardException;
import com.bankInc.CardPayments.service.exceptions.NotExistentProductException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CardServiceImplTest {

	@Autowired
	private CardService cardService;
	
	@Autowired
	private CardDao cardDao;
	
	public static final String ACTIVE_STATUS = "ACTIVE";
	public static final String BLOCKED_STATUS = "BLOCKED";
	
	@Test
	public void whenProductIdExists_thenGeneratesCardNumber() throws NotExistentProductException{

		Integer Productid = 102030;
		int cardNumberLength = 16;
		
		String cardNumber = String.valueOf(cardService.generateCardNumber(Productid));
		
		assertEquals("the length should be 16",cardNumber.length(),cardNumberLength);
		assertEquals("The first 6 digits should match the productId",Integer.valueOf(cardNumber.substring(0, 6)),Productid);
		
	}
	
	@Test
	public void whenProductIdNotExists_thenThrowsNotExistentProductException() {
		
		Exception exception = assertThrows(NotExistentProductException.class, () -> {
			cardService.generateCardNumber(123456);
	    });
		
		 String expectedMessage = "The product with id 123456 does no exist";
		 String actualMessage = exception.getMessage();
		
		 assertTrue("Should throw a NotExistentProductException whit the non eixstent productId",actualMessage.contains(expectedMessage));
		
	}
	
	@Test
	public void whenCardExists_thenTopUpsCard() throws NotActiveCardException, CardNotFoundException{
		BigDecimal balance = new BigDecimal(1000L);
		final Long cardId = 1020301767204038L;
		BigDecimal finalBalance = new BigDecimal(2000L);
		TopupRequest request = setUpTopUpRequest(balance,cardId);
		cardService.topUpCard(request);
		cardService.topUpCard(request);
		
		BigDecimal newBalance = cardDao.GetBalance(cardId);
		
		assertTrue("The card should have a balance of 2000.00",newBalance.compareTo(finalBalance) == 0);
		
	}
	
	@Test
	public void whenCardIsInactive_thenThrowsException() {
		Exception exception = assertThrows(NotActiveCardException.class, () -> {
			final Long inactiveCardId = 1020301767204036L;
			cardService.topUpCard(setUpTopUpRequest(new BigDecimal(1000L),inactiveCardId));
	    });
		 String expectedMessage = "The card with number 1020301767204036 is not active";
		 String actualMessage = exception.getMessage();
		 assertTrue("The message should include the cardId",actualMessage.contains(expectedMessage));
		
	}
	
	@Test
	public void whenCardNotExists_thenThrowsException() {
		Exception exception = assertThrows(CardNotFoundException.class, () -> {
			final Long nonExistingCardId = 999L;
			cardService.topUpCard(setUpTopUpRequest(new BigDecimal(1000L),nonExistingCardId));
	    });
		 String expectedMessage = "Card with number 999 not found";
		 String actualMessage = exception.getMessage();
		 assertTrue("The message should include the cardId",actualMessage.contains(expectedMessage));
		
	}
	
	
	private TopupRequest setUpTopUpRequest(BigDecimal balance, Long cardId){
		TopupRequest request = new TopupRequest();
		request.setBalance(balance);
		request.setCardId(cardId);
		return request;
	}
	
	@Test
	public void whenCardExists_thenGetBalance() throws CardNotFoundException{
		
		final Long cardId = 1020301767204041L;
		BigDecimal expectedBalance = new BigDecimal(1500L);
		
		BigDecimal balance = cardService.getBalance(cardId);
		
		assertTrue("The card should have a balance of 1500.00",expectedBalance.compareTo(balance) == 0);
		
	}
	
	@Test
	public void whenCardNotExists_thenThrowException(){
		
		final Long cardId = 888L;
		Exception exception = assertThrows(CardNotFoundException.class, () -> {
			cardService.getBalance(cardId);
		});
		
		String expectedMessage = "Card with number 888 not found";
		String actualMessage = exception.getMessage();
	
		assertTrue("The message should include the cardId",actualMessage.contains(expectedMessage));
		
	}
	
	@Test
	public void whenCardExists_thenErollCard() throws CardNotFoundException{
		
		final Long cardId = 1020301767204036L;
		EnrollRequest request = setUpEnrollRequest(cardId);
		cardService.enrollCard(request);
		Card card = cardDao.findByNumber(cardId).get();
		assertTrue("The card status should be ACTIVE",card.getStatus().equals(ACTIVE_STATUS));
		
	}
	
	@Test
	public void whenCardToEnrollNotExists_thenThrowException(){
		
		final Long cardId = 777L;
		EnrollRequest request = setUpEnrollRequest(cardId);
		Exception exception = assertThrows(CardNotFoundException.class, () -> {
			cardService.enrollCard(request);
		});
		
		String expectedMessage = "Card with number 777 not found";
		String actualMessage = exception.getMessage();
		assertTrue("The message should include the cardId",actualMessage.contains(expectedMessage));
		
	}
	
	private EnrollRequest setUpEnrollRequest(Long cardId){
		EnrollRequest request = new EnrollRequest();
		request.setCardId(cardId);
		return request;
	}
	
	@Test
	public void whenCardExists_thenBlockCard() throws CardNotFoundException{
		
		final Long cardId = 1020301767204036L;
		cardService.blockCard(cardId);
		Card card = cardDao.findByNumber(cardId).get();
		assertTrue("The card status should be BLOCKED",card.getStatus().equals(BLOCKED_STATUS));
		
	}
	
	@Test
	public void whenCardToBlockNotExists_thenThrowException(){
		
		final Long cardId = 444L;
		Exception exception = assertThrows(CardNotFoundException.class, () -> {
			cardService.blockCard(cardId);
		});
		
		String expectedMessage = "Card with number 444 not found";
		String actualMessage = exception.getMessage();
		assertTrue("The message should include the cardId",actualMessage.contains(expectedMessage));
		
	}
	

	
}

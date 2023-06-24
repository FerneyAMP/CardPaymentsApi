package com.bankInc.CardPayments.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bankInc.CardPayments.model.request.AnulationRequest;
import com.bankInc.CardPayments.model.request.PurchaseRequest;
import com.bankInc.CardPayments.repository.CardDao;
import com.bankInc.CardPayments.service.exceptions.CardNotFoundException;
import com.bankInc.CardPayments.service.exceptions.InsufficientFundsException;
import com.bankInc.CardPayments.service.exceptions.NotActiveCardException;
import com.bankInc.CardPayments.service.exceptions.NotValidRefundException;
import com.bankInc.CardPayments.service.exceptions.TransactionNotFoundException;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceImplTest {

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private CardDao cardDao;
	
	
	@Test
	public void whenCardExistsAndIsActiveAndHasFunds_thenMakePurchase() throws CardNotFoundException, InsufficientFundsException, NotActiveCardException{
		
		Long cardId = 1020301767204040L;
		BigDecimal purchaseValue = new BigDecimal(100L);
		PurchaseRequest request = setUpRequest(purchaseValue,cardId);
		Integer transactionId = transactionService.makePurchase(request);
		
		assertNotNull("Should get the transactionId of the purchase",transactionId);
		
	}
	
	@Test
	public void whenCardNotExists_thenThrowsException(){
		Long nonExistentCardId = 999L;
		BigDecimal purchaseValue = new BigDecimal(100L);
		PurchaseRequest request = setUpRequest(purchaseValue,nonExistentCardId);
		
		Exception exception = assertThrows(CardNotFoundException.class, () -> {
			transactionService.makePurchase(request);
	    });
		
		String expectedMessage = "Card with number 999 not found";
		String actualMessage = exception.getMessage();
		assertTrue("The message should include the cardId",actualMessage.contains(expectedMessage));
		
	}
	
	@Test
	public void whenCardIsInactive_thenThrowsException(){
		Long InactiveCardId = 1020301767204039L;
		BigDecimal purchaseValue = new BigDecimal(100L);
		PurchaseRequest request = setUpRequest(purchaseValue,InactiveCardId);
		
		Exception exception = assertThrows(NotActiveCardException.class, () -> {
			transactionService.makePurchase(request);
	    });
		
		String expectedMessage = "The card with number 1020301767204039 is not active";
		String actualMessage = exception.getMessage();
		assertTrue("The message should include the cardId",actualMessage.contains(expectedMessage));
		
	}
	
	@Test
	public void whenCardHasNotEoughFunds_thenThrowsException(){
		final Long CardId = 1020301767204037L;
		BigDecimal purchaseValue = new BigDecimal(100L);
		PurchaseRequest request = setUpRequest(purchaseValue,CardId);
		
		Exception exception = assertThrows(InsufficientFundsException.class, () -> {
			transactionService.makePurchase(request);
	    });
		
		String expectedMessage = "The card with number 1020301767204037 has no funds";
		String actualMessage = exception.getMessage();
		assertTrue("The message should include the cardId",actualMessage.contains(expectedMessage));
		
	}
	
	private PurchaseRequest setUpRequest(BigDecimal price, Long cardId){
		PurchaseRequest request = new PurchaseRequest();
		request.setPrice(price);
		request.setCardId(cardId);
		return request;
	}
	
	@Test
	public void whenAnulationRequestIsValid_thenMakeAnulation() throws NotValidRefundException, TransactionNotFoundException {
		
		final Long cardId = 1020301767204037L;
		final Integer trnasactionId = 999;
		BigDecimal ExpectedBalance = new BigDecimal(500L);
		
		AnulationRequest request = setUpAnulationRequest(cardId,trnasactionId);
		transactionService.cancelTransaction(request);
		
		BigDecimal balance = cardDao.GetBalance(cardId);
		
		assertTrue("The balance after the cancelation should be 500.00",ExpectedBalance.compareTo(balance) == 0);
		
	}
	
	@Test
	public void whenAnulationRequestIsNotValid_thenThrowException() {
		final Long CardId = 1020301767204040L;
		final Integer transactionId = 999;
		AnulationRequest request = setUpAnulationRequest(CardId, transactionId);
		
		Exception exception = assertThrows(NotValidRefundException.class, () -> {
			transactionService.cancelTransaction(request);
	    });
		
		String expectedMessage = "The refund is not valid";
		String actualMessage = exception.getMessage();
		assertTrue("The message should say that the refund is not valid",actualMessage.contains(expectedMessage));
		
	}
	
	@Test
	public void whenTransactionNotExists_thenThrowException() {
		
		final Long CardId = 1020301767204040L;
		final Integer transactionId = -1;
		AnulationRequest request = setUpAnulationRequest(CardId,transactionId);
		
		Exception exception = assertThrows(TransactionNotFoundException.class, () -> {
			transactionService.cancelTransaction(request);
	    });
		
		String expectedMessage = "Transaction with id -1 not found";
		String actualMessage = exception.getMessage();
		assertTrue("The message should include the transactionId",actualMessage.contains(expectedMessage));
		
	}
	
	private AnulationRequest setUpAnulationRequest(Long cardId, Integer transactionId){
		AnulationRequest request = new AnulationRequest();
		request.setCardId(cardId);
		request.setTransactionId(transactionId);
		return request;
	}
}

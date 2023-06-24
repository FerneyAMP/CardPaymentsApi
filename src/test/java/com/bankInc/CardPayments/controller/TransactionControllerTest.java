package com.bankInc.CardPayments.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.bankInc.CardPayments.model.Transaction;
import com.bankInc.CardPayments.model.request.PurchaseRequest;
import com.bankInc.CardPayments.model.request.AnulationRequest;
import com.bankInc.CardPayments.service.TransactionService;
import com.bankInc.CardPayments.service.exceptions.CardNotFoundException;
import com.bankInc.CardPayments.service.exceptions.InsufficientFundsException;
import com.bankInc.CardPayments.service.exceptions.NotActiveCardException;
import com.bankInc.CardPayments.service.exceptions.NotValidRefundException;
import com.bankInc.CardPayments.service.exceptions.TransactionNotFoundException;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
	
	@MockBean
	private TransactionService transactionService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void shouldReturnTransacionIdWhenPurchaseIsMade() throws Exception{
		Mockito.when(transactionService.makePurchase(any(PurchaseRequest.class))).thenReturn(123456);
		final String jsonPurchaseRequest = "{\"cardId\": \"1020305292879279\",\"price\": 1000}";
		
		MvcResult result = this.mockMvc.perform(post("/transaction/purchase").header("Content-Type", "application/json")
				.content(jsonPurchaseRequest)).andDo(print()).andExpect(status().isOk()).andReturn();
		
		String purchaseId = result.getResponse().getContentAsString();
		
		assertTrue("The generated number should match the one given by the service.",purchaseId.equals(String.valueOf(123456)));
	}
	
	@Test
	public void shouldreturn402whenCardHasInsufficientFunds() throws Exception{
		Mockito.when(transactionService.makePurchase(any(PurchaseRequest.class))).thenThrow(new InsufficientFundsException(123L));
		final String jsonPurchaseRequest = "{\"cardId\": \"1020305292879279\",\"price\": 1000}";
		
		this.mockMvc.perform(post("/transaction/purchase").header("Content-Type", "application/json").content(jsonPurchaseRequest))
			.andDo(print()).andExpect(status().isPaymentRequired());
	}
	
	@Test
	public void shouldreturn402whenCardIsNotActive() throws Exception{
		Mockito.when(transactionService.makePurchase(any(PurchaseRequest.class))).thenThrow(new NotActiveCardException(123L));
		final String jsonPurchaseRequest = "{\"cardId\": \"1020305292879279\",\"price\": 1000}";
		
		this.mockMvc.perform(post("/transaction/purchase").header("Content-Type", "application/json").content(jsonPurchaseRequest))
			.andDo(print()).andExpect(status().isPaymentRequired());
	}
	
	@Test
	public void shouldreturn404whenCardNumberNotExists() throws Exception{
		Mockito.when(transactionService.makePurchase(any(PurchaseRequest.class))).thenThrow(new CardNotFoundException(123L));
		final String jsonPurchaseRequest = "{\"cardId\": \"1020305292879279\",\"price\": 1000}";
		
		this.mockMvc.perform(post("/transaction/purchase").header("Content-Type", "application/json").content(jsonPurchaseRequest))
			.andDo(print()).andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldreturnOkwhenTransactionNumberExists() throws Exception{
		Mockito.when(transactionService.getTransaction(any(Integer.class))).thenReturn(new Transaction());
		
		this.mockMvc.perform(get("/transaction/123456")).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void shouldreturn404whenTransactionNumberNotExists() throws Exception{
		Mockito.when(transactionService.getTransaction(any(Integer.class))).thenThrow(new TransactionNotFoundException(123));
		
		this.mockMvc.perform(get("/transaction/123456")).andDo(print()).andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldreturnOkwhenAnulationRequestIsValid() throws Exception{
		doNothing().when(transactionService).cancelTransaction(any(AnulationRequest.class));
		
		final String jsonAnulationRequest = "{\"cardId\": \"1020305292879279\",\"transactionId\": 123}";
		
		this.mockMvc.perform(post("/transaction/anulation").header("Content-Type", "application/json").content(jsonAnulationRequest))
			.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void shouldreturn402whenAnulationRequestIsValid() throws Exception {
		doThrow(new NotValidRefundException()).when(transactionService).cancelTransaction(any(AnulationRequest.class));
		final String jsonAnulationRequest = "{\"cardId\": \"1020305292879279\",\"transactionId\": 123}";
		
		this.mockMvc.perform(post("/transaction/anulation").header("Content-Type", "application/json").content(jsonAnulationRequest))
			.andDo(print()).andExpect(status().isPaymentRequired());
	}
	
	@Test
	public void shouldreturn404whenAnulationRequestCardNumberNotExistst() throws Exception{
		doThrow(new TransactionNotFoundException(123)).when(transactionService).cancelTransaction(any(AnulationRequest.class));
		final String jsonAnulationRequest = "{\"cardId\": \"1020305292879279\",\"transactionId\": 123}";
		
		this.mockMvc.perform(post("/transaction/anulation").header("Content-Type", "application/json").content(jsonAnulationRequest))
			.andDo(print()).andExpect(status().isNotFound());
	}
}

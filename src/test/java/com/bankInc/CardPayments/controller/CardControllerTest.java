package com.bankInc.CardPayments.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bankInc.CardPayments.model.request.TopupRequest;
import com.bankInc.CardPayments.service.CardService;
import com.bankInc.CardPayments.service.exceptions.NotExistentProductException;



@WebMvcTest(CardController.class)
public class CardControllerTest {
	
	@MockBean
	private CardService cardService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void shouldReturnGeneratedCardNumber() throws Exception{
		final Long generatedId = 1234567890123456L;
		Mockito.when(cardService.generateCardNumber(any(Integer.class))).thenReturn(generatedId);
		
		MvcResult result = this.mockMvc.perform(get("/card/102030/number")).andDo(print()).andExpect(status().isOk()).andReturn();
		String cardNumberResult = result.getResponse().getContentAsString();
		assertTrue("The generated number should match the one given by the service.",cardNumberResult.equals(String.valueOf(generatedId)));
	}
	
	@Test
	public void shouldreturn404whenProductIdDoesNotExist() throws Exception{
		Mockito.when(cardService.generateCardNumber(any(Integer.class))).thenThrow(new NotExistentProductException(123));
		
		this.mockMvc.perform(get("/card/123/number")).andDo(print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldReturnOkWhenTopupRequestIsValid() throws Exception{
		doNothing().when(cardService).topUpCard(any(TopupRequest.class));
		
		this.mockMvc.perform(get("/card/balance/1234567890123456")).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void shouldReturnOkWhenBlockRequestIsValid() throws Exception{
		doNothing().when(cardService).blockCard(any(Long.class));
		
		this.mockMvc.perform(delete("/card/1234567890123456")).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void shouldReturnOkWhenTopUpRequestIsValid() throws Exception{
		doNothing().when(cardService).topUpCard(any(TopupRequest.class));
		final String jsonBlockRequest = "{\"cardId\": \"1020305292879279\",\"balance\": 1000}";
		
		this.mockMvc.perform(post("/card/balance").header("Content-Type", "application/json").content(jsonBlockRequest)).andDo(print()).andExpect(status().isOk());
	}
}

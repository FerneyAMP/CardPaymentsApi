package com.bankInc.CardPayments.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import com.bankInc.CardPayments.helpers.ConstantsHelper;
import com.bankInc.CardPayments.model.request.EnrollRequest;
import com.bankInc.CardPayments.model.request.TopupRequest;
import com.bankInc.CardPayments.service.CardService;
import com.bankInc.CardPayments.service.exceptions.CardNotFoundException;
import com.bankInc.CardPayments.service.exceptions.NotActiveCardException;
import com.bankInc.CardPayments.service.exceptions.NotExistentProductException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;


@CrossOrigin
@RestController
@Validated
@RequestMapping("card")
public class CardController {

	private CardService cardService;
	
	
	
	@Autowired
	public CardController(CardService cardService) {
		this.cardService = cardService;
	}

	@GetMapping("/{productId}/number")
	public Long generateCardNumber(@PathVariable Integer productId) throws NotExistentProductException{
		try {
			return cardService.generateCardNumber(productId);
		}catch(NotExistentProductException ex) {
			throw ConstantsHelper.PRODUCT_DOES_NOT_EXISTS_EXCEPTION;
		}
			
	}
	
	@PostMapping("/enroll")
	public void enrollCard(@RequestBody EnrollRequest request){
		try {
			cardService.enrollCard(request);
		} catch (CardNotFoundException ex) {
			throw ConstantsHelper.CARD_DOES_NOT_EXISTS_EXCEPTION;
		}
	}
	
	@DeleteMapping("/{cardId}")
	public void blockCard(@PathVariable("cardId") @Digits(fraction = 0, integer = 16, message=ConstantsHelper.CARD_INVALID_LENGTH_MESSAGE) Long cardId){
		try {
			cardService.blockCard(cardId);
		} catch (CardNotFoundException ex) {
			throw ConstantsHelper.CARD_DOES_NOT_EXISTS_EXCEPTION;
		}
	}
	
	@PostMapping("/balance")
	public void topUpCard(@Valid @RequestBody TopupRequest request, Errors errors) {
		if(errors.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.getAllErrors().get(0).getDefaultMessage());
		}
		try {
			cardService.topUpCard(request);
		}catch(NotActiveCardException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ConstantsHelper.INACTIVE_CARD_MESSAGE, ex);
		}catch(CardNotFoundException ex) {
			throw ConstantsHelper.CARD_DOES_NOT_EXISTS_EXCEPTION;
		}
		
	}
	
	@GetMapping("/balance/{cardId}")
	public BigDecimal getBalance(@PathVariable("cardId") @Digits(fraction = 0, integer = 16, message=ConstantsHelper.CARD_INVALID_LENGTH_MESSAGE) Long cardId ){
		try {
			return cardService.getBalance(cardId);
		} catch (CardNotFoundException ex) {
			throw ConstantsHelper.CARD_DOES_NOT_EXISTS_EXCEPTION;
		}
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ConstantsHelper.INVALID_REQUEST_STRUCTURE_MESSAGE)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
	
    }
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ConstantsHelper.INVALID_PARAMETER_MESSAGE)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
	
    }
	
	
	
	
}

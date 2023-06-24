package com.bankInc.CardPayments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.bankInc.CardPayments.model.Transaction;
import com.bankInc.CardPayments.model.request.AnulationRequest;
import com.bankInc.CardPayments.model.request.PurchaseRequest;
import com.bankInc.CardPayments.service.TransactionService;
import com.bankInc.CardPayments.service.exceptions.CardNotFoundException;
import com.bankInc.CardPayments.service.exceptions.InsufficientFundsException;
import com.bankInc.CardPayments.service.exceptions.NotActiveCardException;
import com.bankInc.CardPayments.service.exceptions.NotValidRefundException;
import com.bankInc.CardPayments.service.exceptions.TransactionNotFoundException;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@Validated
@RequestMapping("transaction")
public class TransactionController {
	
	private TransactionService transactionService;
	
	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("/purchase")
	public Integer makePurchase(@Valid @RequestBody PurchaseRequest request){
	
		try {
			return transactionService.makePurchase(request);
		} catch (CardNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantsHelper.CARD_NOT_FOUND_MESSAGE, ex);
		} catch (InsufficientFundsException ex) {
			throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED, ConstantsHelper.INSUFICCIENT_FUNDS_MESSAGE, ex);
		} catch (NotActiveCardException ex) {
			throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED, ConstantsHelper.INACTIVE_CARD_MESSAGE, ex);
		}
		
	}
	
	@GetMapping("/{transactionId}")
	public Transaction getTransaction(@PathVariable Integer transactionId) {
		try {
			return transactionService.getTransaction(transactionId);
		} catch (TransactionNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantsHelper.TRANSACTION_NOT_FOUND_MESSAGE, ex);
		}
	}
	
	@PostMapping("/anulation")
	public void cancelTransaction(@Valid @RequestBody AnulationRequest request){
		try {
			transactionService.cancelTransaction(request);
		} catch (NotValidRefundException ex) {
			throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED, ConstantsHelper.INVALID_REQUEST_MESSAGE, ex);
		} catch(TransactionNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantsHelper.TRANSACTION_NOT_FOUND_MESSAGE, ex);
		}
		
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ConstantsHelper.INVALID_REQUEST_MESSAGE)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleException(HttpMessageNotReadableException ex) {
	
    }
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ConstantsHelper.INVALID_PARAMETER_MESSAGE)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
	
    }
	
}

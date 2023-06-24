package com.bankInc.CardPayments.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ConstantsHelper {
	public final static ResponseStatusException PRODUCT_DOES_NOT_EXISTS_EXCEPTION = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id de producto no existe.");
	public final static ResponseStatusException CARD_DOES_NOT_EXISTS_EXCEPTION = new ResponseStatusException(HttpStatus.NOT_FOUND, "La tarjeta no encontrada.");
	public final static String CARD_INVALID_LENGTH_MESSAGE = "Longitud invalida de la tarjeta.";
	public final static String INVALID_PARAMETER_MESSAGE = "Valor del parametro no válido.";
	public final static String INVALID_REQUEST_STRUCTURE_MESSAGE = "Estructura de solicitud no válida.";
	public final static String INACTIVE_CARD_MESSAGE = "La tarjeta no está activa.";
	public final static String CARD_NOT_FOUND_MESSAGE = "Tarjeta no encontrada.";
	public final static String INSUFICCIENT_FUNDS_MESSAGE = "Fondos insuficientes.";
	public final static String TRANSACTION_NOT_FOUND_MESSAGE = "Transacción no encontrada.";
	public final static String INVALID_REQUEST_MESSAGE = "Solicitud no valida.";
}

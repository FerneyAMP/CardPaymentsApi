package com.bankInc.CardPayments.helpers.dueDateCalculator;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class ClientCardDueDateGeneratorHelperImpl implements CardDueDateCalculatorHelper{

	@Override
	public Date calculateDueDate(Integer years) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR, years);
		return c.getTime();
	}

}

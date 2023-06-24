package com.bankInc.CardPayments.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bankInc.CardPayments.model.Card;

public interface CardDao extends JpaRepository<Card,Integer> {

	public Optional<Card> findByNumber(Long number);
	 
	@Modifying
	@Query("update Card c set c.status = :status where c.number = :cardId")
	public int saveCardStatus(@Param("status") String status, @Param("cardId") Long integer);
	
	@Modifying
	@Query("update Card c set c.balance = :balance where c.number = :cardId")
	public int saveCardBalance(@Param("balance") BigDecimal status, @Param("cardId") Long integer);
	
	@Query("SELECT c.balance FROM Card c where c.number = :cardId")
    public BigDecimal GetBalance( @Param("cardId") Long integer);
	
}

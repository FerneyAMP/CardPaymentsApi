package com.bankInc.CardPayments.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bankInc.CardPayments.model.Card;
import com.bankInc.CardPayments.model.Transaction;

@Repository
public interface TransactionDao extends JpaRepository<Transaction,Integer>{
	
	public Optional<Transaction> findById(Integer id);
	
	@Modifying
	@Query("update Transaction t set t.status = :status where t.id = :transactionId")
	public void saveTransactionStatus(@Param("status") String status, @Param("transactionId") Integer id);
}

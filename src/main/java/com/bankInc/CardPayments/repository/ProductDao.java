package com.bankInc.CardPayments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankInc.CardPayments.model.Product;

@Repository
public interface ProductDao extends JpaRepository<Product,Integer> {
	
}

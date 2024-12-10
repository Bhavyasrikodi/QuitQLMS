package com.hexa.QuitQ.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexa.QuitQ.entities.Order;
import com.hexa.QuitQ.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Long>{
	Payment findByOrder(Order order);
}
package com.hexa.QuitQ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexa.QuitQ.entities.Cart;
import com.hexa.QuitQ.entities.Customer;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{



}

package com.hexa.QuitQ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexa.QuitQ.entities.Customer;
import com.hexa.QuitQ.entities.Order;
import com.hexa.QuitQ.entities.Seller;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomers(Customer customer);

}

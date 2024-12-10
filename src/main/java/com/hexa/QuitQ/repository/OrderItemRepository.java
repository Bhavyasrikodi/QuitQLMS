package com.hexa.QuitQ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexa.QuitQ.entities.Order;
import com.hexa.QuitQ.entities.OrderItem;
import com.hexa.QuitQ.entities.Product;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrders(Order order);
    List<OrderItem> findByProductsIn(List<Product> products);
}

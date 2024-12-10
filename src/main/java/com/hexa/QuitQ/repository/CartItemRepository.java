package com.hexa.QuitQ.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexa.QuitQ.entities.Cart;
import com.hexa.QuitQ.entities.CartItem;
import com.hexa.QuitQ.entities.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
	 List<CartItem> findByCart(Cart cart);
}

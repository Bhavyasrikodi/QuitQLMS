package com.hexa.QuitQ.Service;

import com.hexa.QuitQ.entities.Cart;
import com.hexa.QuitQ.entities.Customer;
import com.hexa.QuitQ.exception.ResourceNotFoundException;

public interface CartService {
	 Cart createCart(Customer savedCustomer);
	 void updateCartPrice(Cart cart);
	 float getCartTotalPrice(Customer customer) throws ResourceNotFoundException;
	 Cart getCartById(Long id)throws ResourceNotFoundException;
}
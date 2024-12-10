package com.hexa.QuitQ.Service;

import java.util.List;

import com.hexa.QuitQ.entities.Cart;
import com.hexa.QuitQ.entities.CartItem;
import com.hexa.QuitQ.entities.Product;
import com.hexa.QuitQ.exception.ResourceNotFoundException;

public interface CartItemService {
	CartItem addCartItem(Cart cart, Product product, int quantity);
	CartItem updateCartItem(Cart cart, Product product, int quantity)throws ResourceNotFoundException ;
	List<CartItem> getCartItemsByCart(Cart cart);
	int getProductCountInCart(Cart cart);
	boolean isCartEmpty(Cart cart);
	void removeProductFromCart(Cart cart, Long productId) throws ResourceNotFoundException ;
	void clearCart(Cart cart);
	float getTotalPrice(Cart cart);
}
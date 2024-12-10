package com.hexa.QuitQ.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hexa.QuitQ.DTO.CartDto;
import com.hexa.QuitQ.entities.Cart;

@Component
public class CartMapper {

	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private CartItemMapper cartItemMapper;

	// Map Cart entity to CartDto
	public CartDto mapToCartDto(Cart cart) {
	    CartDto cartDto = new CartDto();
	    cartDto.setTotalPrice(cart.getTotal_price());
	    cartDto.setTotal_quantity(cart.getTotal_quantity());
	    
	    // Use CustomerMapper to map Customer to CustomerDto
	    cartDto.setCustomerDto(customerMapper.mapToCustomerDto(cart.getCustomers()));

	    // Use CartItemMapper to map CartItems to CartItemDto
	    cartDto.setCartItems(cartItemMapper.mapToCartItemDtoList(cart.getCartItems()));
	    
	    return cartDto;
	}

	// Map CartDto to Cart entity
	public Cart mapToCart(CartDto cartDto) {
	    Cart cart = new Cart();
	    cart.setTotal_price(cartDto.getTotalPrice());
	    cart.setTotal_quantity(cartDto.getTotal_quantity());
	    
	    // Use CustomerMapper to map CustomerDto to Customer
	    cart.setCustomers(customerMapper.mapToCustomer(cartDto.getCustomerDto()));

	    // Use CartItemMapper to map CartItemDto to CartItems
	    cart.setCartItems(cartItemMapper.mapToCartItemList(cartDto.getCartItems()));
	    
	    return cart;
	}

}

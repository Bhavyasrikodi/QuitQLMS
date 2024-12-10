package com.hexa.QuitQ.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.NotNull;

@JsonPropertyOrder({
	
})
public class CartDto {
	
	@NotNull(message = "Total price cannot be null")
    private float total_price;
	
    private CustomerDto customerDto;
    private List<CartItemDto> cartItems;
    private int total_quantity;
    
	public CartDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CartDto(float totalPrice, CustomerDto customerDto, List<CartItemDto> cartItems, int total_quantity) {
		super();
		this.total_price = totalPrice;
		this.customerDto = customerDto;
		this.cartItems = cartItems;
		this.total_quantity = total_quantity;
	}

	public int getTotal_quantity() {
		return total_quantity;
	}

	public void setTotal_quantity(int total_quantity) {
		this.total_quantity = total_quantity;
	}

	public float getTotalPrice() {
		return total_price;
	}
	public void setTotalPrice(float totalPrice) {
		this.total_price = totalPrice;
	}
	public CustomerDto getCustomerDto() {
		return customerDto;
	}
	public void setCustomerDto(CustomerDto customerDto) {
		this.customerDto = customerDto;
	}
	public List<CartItemDto> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItemDto> cartItems) {
		this.cartItems = cartItems;
	}
    
    
}
package com.hexa.QuitQ.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hexa.QuitQ.DTO.CartItemDto;
import com.hexa.QuitQ.DTO.ProductSellerDto;
import com.hexa.QuitQ.entities.CartItem;
@Component
public class CartItemMapper {

	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ModelMapper modelMapper;

	// Map CartItem to CartItemDto
	public CartItemDto mapToCartItemDto(CartItem cartItem) {

	    ProductSellerDto productSellerDto = productMapper.mapToProductSellerDto(productMapper.mapToProductDto(cartItem.getProduct()));
	    // Use ProductMapper to map Product to ProductDto
	    CartItemDto cartItemDto = this.modelMapper.map(productSellerDto, CartItemDto.class);	    
	    cartItemDto.setCart_quantity(cartItem.getCart_quantity());
	    
	    return cartItemDto;
	}

	// Map CartItemDto to CartItem
	public CartItem mapToCartItem(CartItemDto cartItemDto) {
	    CartItem cartItem = new CartItem();
	        
	    cartItem.setCart_quantity(cartItemDto.getCart_quantity());
	    
	    return cartItem;
	}

	// Map List of CartItems to List of CartItemDtos
	public List<CartItemDto> mapToCartItemDtoList(List<CartItem> cartItems) {
	    return cartItems.stream()
	            .map(this::mapToCartItemDto)
	            .collect(Collectors.toList());
	}

	// Map List of CartItemDtos to List of CartItems
	public List<CartItem> mapToCartItemList(List<CartItemDto> cartItemDtos) {
	    return cartItemDtos.stream()
	            .map(this::mapToCartItem)
	            .collect(Collectors.toList());
	}

}
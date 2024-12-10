package com.hexa.QuitQ.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hexa.QuitQ.mapper.CustomerMapper;
import com.hexa.QuitQ.mapper.CustomerUserMapper;
import com.hexa.QuitQ.mapper.OrderItemMapper;
import com.hexa.QuitQ.mapper.PaymentMapper;
import com.hexa.QuitQ.mapper.ProductMapper;
import com.hexa.QuitQ.mapper.SellerMapper;

@Configuration
public class MapperConfig {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	@Bean
	public SellerMapper sellerMapper() {
		return new SellerMapper();
	}
	@Bean
	public ProductMapper productMapper() {
		return new ProductMapper();
	}
	
	@Bean
	public OrderItemMapper orderItemMapper() {
		return new OrderItemMapper();
	}
	
	@Bean
	public CustomerMapper customerMapper() {
		return new CustomerMapper();
	}
	
	@Bean
	public CustomerUserMapper customerUserMapper() {
		return new CustomerUserMapper();
	}
	
	@Bean
	public PaymentMapper paymentMapper() {
		return new PaymentMapper();
	}
}

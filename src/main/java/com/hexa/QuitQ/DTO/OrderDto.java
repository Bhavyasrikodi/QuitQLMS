package com.hexa.QuitQ.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hexa.QuitQ.enums.PaymentMode;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@JsonPropertyOrder({
	"customer_name",
	"email",
	"phone_number",
	"paymentMode",
	"totalOrderAmount",
	"shippingAddress",
	"orderDate",
	"orderItems"	
})
public class OrderDto {
    private float totalOrderAmount;
    private String shippingAddress;
    private LocalDateTime orderDate;
    private List<OrderItemDto> orderItems;
    
    @JsonProperty("customer_name")
    private String user_name;
	private Long phone_number;
	private String email;
	
	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;
	
    public OrderDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderDto(float totalOrderAmount, String shippingAddress, LocalDateTime orderDate,
			List<OrderItemDto> orderItems, String user_name, Long phone_number, String email, PaymentMode paymentMode) {
		super();
		this.totalOrderAmount = totalOrderAmount;
		this.shippingAddress = shippingAddress;
		this.orderDate = orderDate;
		this.orderItems = orderItems;
		this.user_name = user_name;
		this.phone_number = phone_number;
		this.email = email;
		this.paymentMode = paymentMode;
	}

	public float getTotalOrderAmount() {
		return totalOrderAmount;
	}

	public void setTotalOrderAmount(float totalOrderAmount) {
		this.totalOrderAmount = totalOrderAmount;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public List<OrderItemDto> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDto> orderItems) {
		this.orderItems = orderItems;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Long getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(Long phone_number) {
		this.phone_number = phone_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	

}

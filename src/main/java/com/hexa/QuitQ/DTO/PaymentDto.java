package com.hexa.QuitQ.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hexa.QuitQ.enums.PaymentMode;
import com.hexa.QuitQ.enums.PaymentStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@JsonPropertyOrder({
    "user_name", 
    "phone_number", 
    "email", 
    "orderItems", 
    "totalOrderAmount", 
    "shippingAddress", 
    "orderDate", 
    "orderStatus", 
    "amount", 
    "transaction_id", 
    "paymentMode", 
    "paymentStatus",
	"paymentId"
})
public class PaymentDto {

	@JsonProperty("payment_amount")
	private float amount;
	private String paymentModeNumber;
	private long paymentId;
	
	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;
	
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	
	private float totalOrderAmount;
    private String shippingAddress;
    private LocalDateTime orderDate;
    private List<OrderItemDto> orderItems;
    
    @JsonProperty("customer_name")
    private String user_name;
	private Long phone_number;
	private String email;

	

	
	public PaymentDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public PaymentDto(float amount, String paymentModeNumber, PaymentMode paymentMode,
			PaymentStatus paymentStatus, float totalOrderAmount, String shippingAddress, LocalDateTime orderDate,
			 List<OrderItemDto> orderItems, String user_name, Long phone_number, String email, long paymentId) {
		super();
		this.amount = amount;
		this.paymentModeNumber = paymentModeNumber;
		this.paymentMode = paymentMode;
		this.paymentStatus = paymentStatus;
		this.totalOrderAmount = totalOrderAmount;
		this.shippingAddress = shippingAddress;
		this.orderDate = orderDate;
		this.orderItems = orderItems;
		this.user_name = user_name;
		this.phone_number = phone_number;
		this.email = email;
		this.paymentId = paymentId;
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



	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	

	public String getPaymentModeNumber() {
		return paymentModeNumber;
	}



	public void setPaymentModeNumber(String paymentModeNumber) {
		this.paymentModeNumber = paymentModeNumber;
	}



	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }



	
}

package com.hexa.QuitQ.DTO;

import com.hexa.QuitQ.enums.PaymentMode;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class CustomerDto {
	
	private String user_name;
	private String address;
	private Long phone_number; 
	private String email;
	private long customer_id;


	
	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;
	

	public CustomerDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public CustomerDto(String user_name, String address, Long phone_number, String email, PaymentMode paymentMode, long customer_id) {
		super();
		this.user_name = user_name;
		this.address = address;
		this.phone_number = phone_number;
		this.email = email;
		this.paymentMode = paymentMode;
		this.customer_id = customer_id;
	}


	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}


	public String getUser_name() {
		return user_name;
	}


	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
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

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }



	


	
}
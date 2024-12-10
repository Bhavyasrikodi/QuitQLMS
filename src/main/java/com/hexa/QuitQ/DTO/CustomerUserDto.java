package com.hexa.QuitQ.DTO;

import java.util.UUID;

import com.hexa.QuitQ.enums.PaymentMode;
import com.hexa.QuitQ.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CustomerUserDto {
	@NotEmpty(message= "user_name should not be empty")
	private String user_name;
	
	@NotNull(message = "address should not be empty")
	private String address;
	
	@NotNull(message = "phone number should not be empty")
	private Long phone_number;
	 
	@NotEmpty(message="email should not be empty")
	private String email;
	
	@NotEmpty(message ="password should not be empty")
	@Size(min = 7, max = 18, message = "Password must be between 7 and 12 characters long")
	private String password;
	
    @NotNull(message = "user role should not be empty")
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	
    @NotNull(message = "payment Mode should not be empty")
	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;

	private UUID partnerId;

	
	
	public CustomerUserDto() {
		super();
		// TODO Auto-generated constructor stub
	}



	public CustomerUserDto(String name, String address, Long phone_number, String email, String password,
			UserRole userRole, PaymentMode paymentMode) {
		super();
		this.user_name = name;
		this.address = address;
		this.phone_number = phone_number;
		this.email = email;
		this.password = password;
		this.userRole = userRole;
		this.paymentMode = paymentMode;
	}



	public String getUser_name() {
		return user_name;
	}



	public void setUser_name(String name) {
		this.user_name = name;
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



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public UserRole getUserRole() {
		return userRole;
	}



	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}



	public PaymentMode getPaymentMode() {
		return paymentMode;
	}



	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

    public UUID getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(UUID partnerId) {
        this.partnerId = partnerId;
    }
	
	
}
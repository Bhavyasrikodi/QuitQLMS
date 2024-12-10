package com.hexa.QuitQ.DTO;

import java.util.UUID;

import com.hexa.QuitQ.enums.UserRole;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class UserDto {
	private String user_name;
	private String address;
	private Long phone_number;
	private String email;
	
	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	public UserDto(String name, String address, Long phone_number, String email, UserRole userRole) {
		super();
		this.user_name = name;
		this.address = address;
		this.phone_number = phone_number;
		this.email = email;
		this.userRole = userRole;
	}

	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
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

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	
	
}

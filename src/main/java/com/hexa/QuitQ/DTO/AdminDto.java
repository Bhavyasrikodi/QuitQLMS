package com.hexa.QuitQ.DTO;

import com.hexa.QuitQ.enums.AdminRole;
import com.hexa.QuitQ.enums.UserRole;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class AdminDto {
	@Enumerated(EnumType.STRING)
	private AdminRole adminRole;
	
	private String user_name;
	private String address;
	private Long phone_number;
	private String email;
	private UserRole userRole;
	
	public AdminDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdminDto(AdminRole adminRole, String user_name, String address, Long phone_number, String email,
			UserRole userRole) {
		super();
		this.adminRole = adminRole;
		this.user_name = user_name;
		this.address = address;
		this.phone_number = phone_number;
		this.email = email;
		this.userRole = userRole;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
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

	public AdminRole getAdminRole() {
		return adminRole;
	}

	public void setAdminRole(AdminRole adminRole) {
		this.adminRole = adminRole;
	}

	
}
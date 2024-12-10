package com.hexa.QuitQ.entities;

import java.util.UUID;

import com.hexa.QuitQ.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;


@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long user_id;
	private String user_name;
	private String address;
	private Long phone_number;
	
	@Column(name = "email", nullable = false, unique = true) 
    @Email
	private String email;
	
	@Column(name = "password", nullable = false) 
	//@Size(min = 7, max = 18, message = "Password must be between 7 and 12 characters long")
	private String password;
	
	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	private UUID partnerId;
	
    //Constructors
	public User() {
		super();
	}
       
    public User(String name, String address, Long phone_number, String email, String password, UserRole userRole) {
		super();
		this.user_name = name;
		this.address = address;
		this.phone_number = phone_number;
		this.email = email;
		this.password = password;
		this.userRole = userRole;
	}

	//Getters and Setters
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
    
	public Long getUser_id() {
		return user_id;
	}

    public UUID getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(UUID partnerId) {
        this.partnerId = partnerId;
    }

}



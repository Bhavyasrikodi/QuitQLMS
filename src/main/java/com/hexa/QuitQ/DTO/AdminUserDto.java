package com.hexa.QuitQ.DTO;

import com.hexa.QuitQ.enums.AdminRole;
import com.hexa.QuitQ.enums.UserRole;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AdminUserDto {
	@NotNull(message= "user_name should not be empty")
	private String user_name;
	
	private String address;
	
	@NotNull(message = "phone number should not be empty")
	private Long phone_number;
	 

	@NotNull(message="email should not be empty")
	private String email;
	
    @NotNull(message ="password should not be empty")
	@Size(min = 7, max = 18, message = "Password must be between 7 and 12 characters long")
	private String password;
	
    @NotNull(message = "user role should not be empty")
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
    
    @Enumerated(EnumType.STRING)
    private AdminRole adminRole;

    public AdminUserDto() {
        super();
    }

    public AdminUserDto(String user_name, String address, Long phone_number, String email, String password,
                        UserRole userRole, AdminRole adminRole) {
        this.user_name = user_name;
        this.address = address;
        this.phone_number = phone_number;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.adminRole = adminRole;
    }

    // Getters and Setters
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

    public AdminRole getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(AdminRole adminRole) {
        this.adminRole = adminRole;
    }
}
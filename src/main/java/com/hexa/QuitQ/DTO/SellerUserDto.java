package com.hexa.QuitQ.DTO;

import com.hexa.QuitQ.enums.ShippingMode;
import com.hexa.QuitQ.enums.UserRole;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class SellerUserDto {
	@NotEmpty(message= "user_name should not be empty")
	private String user_name;
	
	@NotEmpty(message= "address should not be empty")
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
    
    @NotEmpty(message = "Store name cannot be empty")
    private String store_name;

    @NotNull(message = "Account number cannot be empty")
    private Long account_number;

    @NotEmpty(message = "IFSC code cannot be empty")
    private String IFSC_code;

    @NotEmpty(message = "GST number cannot be empty")
    private String GST_number;
	
    @NotNull(message = "ShippingMode cannot be empty")
	@Enumerated(EnumType.STRING)
	private ShippingMode shippingMode;
	
	


public SellerUserDto() {
		super();
		// TODO Auto-generated constructor stub
	}

		public SellerUserDto(UserRole userRole, String name, String address, Long phone_number, String email,
			String password, String store_name, Long account_number, String iFSC_code, String gST_number,
			ShippingMode shippingMode) {
		super();
		this.userRole = userRole;
		this.user_name = name;
		this.address = address;
		this.phone_number = phone_number;
		this.email = email;
		this.password = password;
		this.store_name = store_name;
		this.account_number = account_number;
		this.IFSC_code = iFSC_code;
		this.GST_number = gST_number;
		this.shippingMode = shippingMode;
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

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public Long getAccount_number() {
		return account_number;
	}

	public void setAccount_number(Long account_number) {
		this.account_number = account_number;
	}

	public String getIFSC_code() {
		return IFSC_code;
	}

	public void setIFSC_code(String iFSC_code) {
		this.IFSC_code = iFSC_code;
	}

	public String getGST_number() {
		return GST_number;
	}

	public void setGST_number(String gST_number) {
		this.GST_number = gST_number;
	}

	public ShippingMode getShippingMode() {
		return shippingMode;
	}

	public void setShippingMode(ShippingMode shippingMode) {
		this.shippingMode = shippingMode;
	}
	
	
	
}

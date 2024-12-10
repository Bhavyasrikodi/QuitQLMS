package com.hexa.QuitQ.DTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.hexa.QuitQ.enums.ShippingMode;
import com.hexa.QuitQ.enums.UserRole;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
@JsonPropertyOrder({
	"userDto",
    "store_name",
    "account_number",
    "ifsc_code",
    "gst_number",
    "shippingMode"    
})
public class SellerDto {

	private String store_name;
	private Long account_number;
	private String IFSC_code;
	private String GST_number;
	
	@Enumerated(EnumType.STRING)
	private ShippingMode shippingMode;
	
	private String user_name;
	private String address;
	private Long phone_number;
	private String email;
	
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	

	public SellerDto() {
		super();
	}
		
	
	public SellerDto(String store_name, Long account_number, String iFSC_code, String gST_number,
			ShippingMode shippingMode, String user_name, String address, Long phone_number, String email,
			UserRole userRole) {
		super();
		this.store_name = store_name;
		this.account_number = account_number;
		IFSC_code = iFSC_code;
		GST_number = gST_number;
		this.shippingMode = shippingMode;
		this.user_name = user_name;
		this.address = address;
		this.phone_number = phone_number;
		this.email = email;
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

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
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

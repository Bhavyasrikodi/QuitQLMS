package com.hexa.QuitQ.DTO;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OrderRequestDto
{
	@NotNull(message = "Customer ID cannot be null")
    private Long customerId;

    @NotEmpty(message = "Shipping address cannot be empty")
    private String shippingAddress;

    @NotEmpty(message = "Product IDs cannot be empty")
    private List<Long> productIds;

    @NotEmpty(message = "Quantities cannot be empty")
    private List<Integer> quantities;
    
    
    
	public OrderRequestDto(Long customerId, String shippingAddress, List<Long> productIds, List<Integer> quantities
			) {
		super();
		this.customerId = customerId;
		this.shippingAddress = shippingAddress;
		this.productIds = productIds;
		this.quantities = quantities;
	}



	public OrderRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public List<Long> getProductIds() {
		return productIds;
	}
	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}
	public List<Integer> getQuantities() {
		return quantities;
	}
	public void setQuantities(List<Integer> quantities) {
		this.quantities = quantities;
	}

    // Getters and setters
}


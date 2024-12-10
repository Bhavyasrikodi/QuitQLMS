package com.hexa.QuitQ.DTO;

import com.hexa.QuitQ.enums.PaymentMode;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PaymentRequestDto {
	
	
	
	@NotNull(message = "Customer ID cannot be null")
    private Long customerId;

    @NotEmpty(message = "Shipping address cannot be empty")
    @Size(min = 1, max = 255, message = "Shipping address must be between 1 and 255 characters")
    private String shippingAddress;

    @NotEmpty(message = "Payment Mode Number cannot be empty")
    @Size(min = 1, max = 100, message = "paymentModeNumber must be between 1 and 100 characters")
    private String paymentModeNumber;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Payment mode cannot be null")
    private PaymentMode paymentMode;
    

    private Long productId;
    private int productQuantity;
    
	public PaymentRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PaymentRequestDto(Long customerId, String shippingAddress, Long productId, int productQuantity,
			String paymentModeNumber, PaymentMode paymentMode) {
		super();
		this.customerId = customerId;
		this.shippingAddress = shippingAddress;
		this.productId = productId;
		this.productQuantity = productQuantity;
		this.paymentModeNumber = paymentModeNumber;
		this.paymentMode = paymentMode;
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

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
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


}

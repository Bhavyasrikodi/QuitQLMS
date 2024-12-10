package com.hexa.QuitQ.Service;

import com.hexa.QuitQ.DTO.PaymentRequestDto;
import com.hexa.QuitQ.entities.Payment;
import com.hexa.QuitQ.enums.PaymentStatus;
import com.hexa.QuitQ.exception.ResourceNotFoundException;

public interface PaymentService{
	
	Payment createPaymentforBuyNow(PaymentRequestDto paymentRequestDto) throws ResourceNotFoundException;
	Payment createPaymentforCart(PaymentRequestDto paymentRequestDto) throws ResourceNotFoundException;
	Payment findPaymentDetails(Long Order_id) throws ResourceNotFoundException;
	PaymentStatus getPaymentStatus(Long payment_id) throws ResourceNotFoundException;

}

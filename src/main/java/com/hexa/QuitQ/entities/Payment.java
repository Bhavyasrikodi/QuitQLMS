package com.hexa.QuitQ.entities;

import com.hexa.QuitQ.enums.PaymentMode;
import com.hexa.QuitQ.enums.PaymentStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="payments")
public class Payment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long payment_id;
	
	private float amount;
	private String paymentModeNumber;
	
	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;
	
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	
    @OneToOne()
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

	@ManyToOne()
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
	   
    //Constructors
	public Payment() {
		super();
	}

	public Payment(Long payment_id, float amount, String paymentModeNumber, PaymentMode paymentMode, PaymentStatus paymentStatus) {
		super();
		this.payment_id = payment_id;
		this.amount = amount;
		this.paymentModeNumber = paymentModeNumber;
		this.paymentMode = paymentMode;
		this.paymentStatus = paymentStatus;
	}

	public Long getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(Long payment_id) {
		this.payment_id = payment_id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
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

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	} 
	
}

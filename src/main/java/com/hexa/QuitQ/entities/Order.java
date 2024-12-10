package com.hexa.QuitQ.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.hexa.QuitQ.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
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


@Entity
@Table(name="Orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long order_id;
	private float total_order_amount;
	private String shipping_address;
	private int order_quantity;
	
	@CreationTimestamp
	private LocalDateTime order_date;
	
	
	@ManyToOne
    @JoinColumn(name = "customer_id")
	private Customer customers;

	@ElementCollection()
	private List<OrderItem> orderItems;

	@OneToOne(mappedBy = "order", cascade = CascadeType.REMOVE) 
    private Payment payment;
	
	
	
    //Constructors
	public Order() {
		super();
	}

	public Order(Long order_id, float total_order_amount, String shipping_address, LocalDateTime order_date,
			 int order_quantity) {
		super();
		this.order_id = order_id;
		this.total_order_amount = total_order_amount;
		this.shipping_address = shipping_address;
		this.order_date = order_date;
		this.order_quantity = order_quantity;
	}


	public int getOrder_quantity() {
		return order_quantity;
	}

	public void setOrder_quantity(int order_quantity) {
		this.order_quantity = order_quantity;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}

	public Long getOrder_id() {
		return order_id;
	}

	public float getTotal_order_amount() {
		return total_order_amount;
	}

	public void setTotal_order_amount(float total_order_amount) {
		this.total_order_amount = total_order_amount;
	}

	public String getShipping_address() {
		return shipping_address;
	}

	public void setShipping_address(String shipping_address) {
		this.shipping_address = shipping_address;
	}

	public LocalDateTime getOrder_date() {
		return order_date;
	}

	public void setOrder_date(LocalDateTime order_date) {
		this.order_date = order_date;
	}

	

	public Customer getCustomers() {
		return customers;
	}

	public void setCustomers(Customer customers) {
		this.customers = customers;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	
	
	
    
}
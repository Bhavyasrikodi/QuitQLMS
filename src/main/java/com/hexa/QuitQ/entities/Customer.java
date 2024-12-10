package com.hexa.QuitQ.entities;

import java.util.List;

import com.hexa.QuitQ.enums.PaymentMode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="customers")
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long customer_id;
	
	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;
	
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User users;

    @OneToMany(mappedBy = "customers",cascade = CascadeType.REMOVE)
    private List<Order> orders;

    @OneToOne(mappedBy = "customers",cascade = CascadeType.REMOVE)
    private Cart cart;
    
    @OneToMany(mappedBy = "customer")
    private List<Payment> paymentsList;
	
    
    //Constructors
    public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Customer(Long customer_id, PaymentMode paymentMode) {
		super();
		this.customer_id = customer_id;
		this.paymentMode = paymentMode;
	}



	public Long getCustomer_id() {
		return customer_id;
	}



	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}



	public PaymentMode getPaymentMode() {
		return paymentMode;
	}



	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}



	public User getUsers() {
		return users;
	}



	public void setUsers(User users) {
		this.users = users;
	}



	public List<Order> getOrders() {
		return orders;
	}



	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}



	public Cart getCart() {
		return cart;
	}



	public void setCart(Cart cart) {
		this.cart = cart;
	}



	public List<Payment> getPaymentsList() {
		return paymentsList;
	}



	public void setPaymentsList(List<Payment> paymentsList) {
		this.paymentsList = paymentsList;
	}
    
    

}
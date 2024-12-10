package com.hexa.QuitQ.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="CartItems")
public class CartItem {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cart_item_id;  // Cart Item ID

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;  // Associated cart

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;  // Associated product

    private int cart_quantity; // Quantity of the product


	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartItem(Long cart_item_id, Cart cart, Product product, int cart_quantity) {
		super();
		this.cart_item_id = cart_item_id;
		this.cart = cart;
		this.product = product;
		this.cart_quantity = cart_quantity;
	}

	public Long getCart_item_id() {
		return cart_item_id;
	}

	public void setCart_item_id(Long cart_item_id) {
		this.cart_item_id = cart_item_id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getCart_quantity() {
		return cart_quantity;
	}

	public void setCart_quantity(int cart_quantity) {
		this.cart_quantity = cart_quantity;
	}

    
}
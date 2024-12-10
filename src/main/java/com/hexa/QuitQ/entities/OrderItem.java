package com.hexa.QuitQ.entities;

import com.hexa.QuitQ.enums.ItemType;
import com.hexa.QuitQ.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name="OrderItems")
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long order_items_id;
	private int order_item_quantity;
	private float total_price;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	
	@Enumerated(EnumType.STRING)
	private ItemType itemType;
	
	@ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "order_id")
	private Order orders;

	@ManyToOne
    @JoinColumn(name = "product_id")
	private Product products;

	@ManyToOne
    @JoinColumn(name = "cart_id")
	private Cart cart;

	
    //Constructors
	public OrderItem() {
		super();
	}


	public OrderItem(Long order_items_id, int order_item_quantity, float total_price, OrderStatus orderStatus,
			ItemType itemType, Order orders, Product products, Cart cart) {
		super();
		this.order_items_id = order_items_id;
		this.order_item_quantity = order_item_quantity;
		this.total_price = total_price;
		this.orderStatus = orderStatus;
		this.itemType = itemType;
		this.orders = orders;
		this.products = products;
		this.cart = cart;
	}


	public Long getOrder_items_id() {
		return order_items_id;
	}


	public void setOrder_items_id(Long order_items_id) {
		this.order_items_id = order_items_id;
	}


	public int getOrder_item_quantity() {
		return order_item_quantity;
	}


	public void setOrder_item_quantity(int order_item_quantity) {
		this.order_item_quantity = order_item_quantity;
	}


	public float getTotal_price() {
		return total_price;
	}


	public void setTotal_price(float total_price) {
		this.total_price = total_price;
	}


	public OrderStatus getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}


	public ItemType getItemType() {
		return itemType;
	}


	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}


	public Order getOrders() {
		return orders;
	}


	public void setOrders(Order orders) {
		this.orders = orders;
	}


	public Product getProducts() {
		return products;
	}


	public void setProducts(Product products) {
		this.products = products;
	}


	public Cart getCart() {
		return cart;
	}


	public void setCart(Cart cart) {
		this.cart = cart;
	}

}
package com.hexa.QuitQ.entities;

import java.util.List;

import com.hexa.QuitQ.enums.ProductCategory;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Products")
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long product_id;
	private String product_name;
	private int Stock;
	private float price;
	private String description;
	private String imgUrl;
	private String brand;
	
	@Enumerated(EnumType.STRING)
	private ProductCategory productCategory;
	
	@ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller sellers;

    @OneToMany(mappedBy = "products",  cascade = {CascadeType.REMOVE})
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}) 
    private List<CartItem> cartItems;
    
    //Constructors
	public Product() {
		super();
	}

	public Product(Long product_id, String name, int stock, float price, String description, String imgUrl, String brand,
			ProductCategory productCategory) {
		super();
		this.product_id = product_id;
		this.product_name = name;
		this.Stock = stock;
		this.price = price;
		this.description = description;
		this.imgUrl = imgUrl;
		this.brand = brand;
		this.productCategory = productCategory;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Long getProduct_id() {
		return product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String name) {
		this.product_name = name;
	}

	public int getStock() {
		return Stock;
	}

	public void setStock(int stock) {
		Stock = stock;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public Seller getSellers() {
		return sellers;
	}

	public void setSellers(Seller sellers) {
		this.sellers = sellers;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	

	

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

    
}
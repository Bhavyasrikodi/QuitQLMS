package com.hexa.QuitQ.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexa.QuitQ.enums.ProductCategory;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class ProductSellerDto {
	
	private Long product_id;
	private String product_name;
	private int stock;
	private float price;
	private String description;
	private String imgUrl;
	private String brand;
	private String store_name;
	private String address;
	
	@Enumerated(EnumType.STRING)
	private ProductCategory productCategory;
	
	
	public ProductSellerDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ProductSellerDto(String product_name, int stock, float price, String description, String imgUrl,
			String brand,String store_name, String address,	ProductCategory productCategory, Long product_id) {
		super();
		this.product_id = product_id;
		this.product_name = product_name;
		this.stock = stock;
		this.price = price;
		this.description = description;
		this.imgUrl = imgUrl;
		this.brand = brand;
		this.store_name = store_name;
		this.address = address;
		this.productCategory = productCategory;
	}
	
	
	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
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
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
	
	
}

package com.hexa.QuitQ.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hexa.QuitQ.enums.ProductCategory;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@JsonPropertyOrder({
	"product_id",
    "product_name", 
    "price", 
    "description", 
    "productCategory", 
    "imgUrl", 
    "brand", 
    "store_name", 
    "store_location", 
    "orderItemQuantity", 
    "totalPrice", 
	"orderStatus",
    "itemType"
})
public class OrderItemDto {
	
	private Long order_items_id;
    private Long product_id;
    private String product_name;
	private float price;
	private String description;
	
	@Enumerated(EnumType.STRING)
	private ProductCategory productCategory;
	
	private String imgUrl;
	private String brand;
	private String store_name;
	
	@JsonProperty("store_location")
	private String address;
	
	private Integer orderItemQuantity;
    private float totalPrice;

    private String orderStatus;
    private String itemType;
    
    public OrderItemDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderItemDto(String product_name, float price, String description, ProductCategory productCategory,
			String imgUrl, String brand, String store_name, String address, Integer orderItemQuantity, float totalPrice,
			String orderStatus, String itemType) {
		super();
		this.product_name = product_name;
		this.price = price;
		this.description = description;
		this.productCategory = productCategory;
		this.imgUrl = imgUrl;
		this.brand = brand;
		this.store_name = store_name;
		this.address = address;
		this.orderItemQuantity = orderItemQuantity;
		this.totalPrice = totalPrice;
		this.orderStatus = orderStatus;
		this.itemType = itemType;
	}
	
	
	
	public Long getOrder_items_id() {
		return order_items_id;
	}

	public void setOrder_items_id(Long order_items_id) {
		this.order_items_id = order_items_id;
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

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
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

	public Integer getOrderItemQuantity() {
		return orderItemQuantity;
	}

	public void setOrderItemQuantity(Integer orderItemQuantity) {
		this.orderItemQuantity = orderItemQuantity;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	


}

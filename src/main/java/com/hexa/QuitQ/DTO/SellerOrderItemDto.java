package com.hexa.QuitQ.DTO;
import java.time.LocalDateTime;

import com.hexa.QuitQ.enums.PaymentStatus;
import com.hexa.QuitQ.enums.ProductCategory;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


public class SellerOrderItemDto {

	private Long order_items_id;
	private Long product_id;
    private String product_name;
    private float price;
    private String description;
    
    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;
    
    private String imgUrl;
    private String brand;
    
    private Integer orderItemQuantity;
    private float totalPrice;
    
    private String orderStatus;  // Include order status as a string
    
    private String customer_name; // Customer name
    private String email; // Customer email
    private Long phone_number; // Customer phone number
    private String paymentMode; // Payment mode
    private PaymentStatus paymentStatus;
    private String shippingAddress; // Shipping address for the order
    private LocalDateTime orderDate; // Date of the order
    
    public SellerOrderItemDto() {
        super();
    }

	public SellerOrderItemDto(Long order_items_id,Long product_id, String product_name, float price, String description,
			ProductCategory productCategory, String imgUrl, String brand, Integer orderItemQuantity, float totalPrice,
			String orderStatus, String customer_name, String email, Long phone_number, String paymentMode, PaymentStatus paymentStatus,
			String shippingAddress, LocalDateTime orderDate) {
		super();
		this.order_items_id = order_items_id;
		this.product_id = product_id;
		this.product_name = product_name;
		this.price = price;
		this.description = description;
		this.productCategory = productCategory;
		this.imgUrl = imgUrl;
		this.brand = brand;
		this.orderItemQuantity = orderItemQuantity;
		this.totalPrice = totalPrice;
		this.orderStatus = orderStatus;
		this.customer_name = customer_name;
		this.email = email;
		this.phone_number = phone_number;
		this.paymentMode = paymentMode;
		this.paymentStatus =paymentStatus;
		this.shippingAddress = shippingAddress;
		this.orderDate = orderDate;
	}




	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	public Long getOrder_items_id() {
		return order_items_id;
	}


	public void setOrder_items_id(Long order_items_id) {
		this.order_items_id = order_items_id;
	}




	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public Long getPhone_number() {
		return phone_number;
	}



	public void setPhone_number(Long phone_number) {
		this.phone_number = phone_number;
	}



	public String getPaymentMode() {
		return paymentMode;
	}



	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}



	public String getShippingAddress() {
		return shippingAddress;
	}



	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}



	public LocalDateTime getOrderDate() {
		return orderDate;
	}



	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
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
}

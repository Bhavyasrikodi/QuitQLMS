package com.hexa.QuitQ.DTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hexa.QuitQ.enums.ProductCategory;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
@JsonPropertyOrder({
	"name",
	"description",
	"price",
	"productCategory",
	"brand",
	"Stock",
	"imgUrl",
	"sellerDto"	
})
public class ProductDto {

	@NotEmpty(message = "Product name should not be empty")
    private String product_name;

    @NotNull(message = "Stock should not be null")
    private Integer stock;

    @NotNull(message = "Price should not be null")
    private Float price;

    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotEmpty(message = "Image URL should not be empty")
    private String imgUrl;

    @NotEmpty(message = "Brand should not be empty")
    private String brand;

    @NotNull(message = "Product category should not be null")
    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;
    
    private Long product_id;
	
	private SellerDto sellerDto;

	public ProductDto(String name, int stock, float price, String description, String imgUrl,String brand,
			ProductCategory productCategory, Long product_id) {
		super();
		this.product_id = product_id;
		this.product_name = name;
		this.stock = stock;
		this.price = price;
		this.description = description;
		this.imgUrl = imgUrl;
		this.brand = brand;
		this.productCategory = productCategory;
	}

	public ProductDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String name) {
		this.product_name = name;
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

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public SellerDto getSellerDto() {
		return sellerDto;
	}

	public void setSellerDto(SellerDto sellerDto) {
		this.sellerDto = sellerDto;
	}

	
	
	
	
}

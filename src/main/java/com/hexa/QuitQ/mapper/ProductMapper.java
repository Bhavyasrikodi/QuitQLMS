package com.hexa.QuitQ.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.hexa.QuitQ.DTO.ProductDto;
import com.hexa.QuitQ.DTO.ProductSellerDto;
import com.hexa.QuitQ.DTO.SellerDto;
import com.hexa.QuitQ.DTO.UserDto;
import com.hexa.QuitQ.entities.Product;
import com.hexa.QuitQ.entities.Seller;

public class ProductMapper {
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private SellerMapper sellerMapper;
	
	public Product mapToProduct(ProductDto productDto) {
		Seller seller = sellerMapper.mapToSeller(productDto.getSellerDto());
		Product product = this.modelmapper.map(productDto, Product.class);
		product.setSellers(seller);
		return product;
	}
	
	public ProductDto mapToProductDto(Product product) {
		SellerDto sellerDto = sellerMapper.mapToSellerDto(product.getSellers());
		ProductDto productDto = this.modelmapper.map(product,ProductDto.class);
		productDto.setSellerDto(sellerDto);
		return productDto;
	}
	
	public List<ProductDto> mapToProductDtoList(List<Product> listOfProducts){
	List<ProductDto> dtoList = new ArrayList<>();
		for(int i=0;i<listOfProducts.size();i++) {
			ProductDto productDto = this.mapToProductDto(listOfProducts.get(i));
			dtoList.add(productDto);
		}
	return dtoList;
	}
	
	public List<Product> mapToProductList(List<ProductDto> dtoList){
		List<Product> listOfProducts = new ArrayList<>();
			for(int i=0;i<dtoList.size();i++) {
				Product product = this.mapToProduct(dtoList.get(i));
				listOfProducts.add(product);
			}
		return listOfProducts;
	}
	
	//productDto to ProductSellerDto
	public ProductSellerDto mapToProductSellerDto(ProductDto pdto) {
		SellerDto sellerDto = pdto.getSellerDto();
		UserDto userDto = this.modelmapper.map(sellerDto, UserDto.class);
		ProductSellerDto psdto = modelmapper.map(userDto, ProductSellerDto.class);
		psdto.setProduct_id(pdto.getProduct_id());
		psdto.setStore_name(sellerDto.getStore_name());
		psdto.setProduct_name(pdto.getProduct_name());;
		psdto.setPrice(pdto.getPrice());
	    psdto.setProductCategory(pdto.getProductCategory());
	    psdto.setBrand(pdto.getBrand());
	    psdto.setImgUrl(pdto.getImgUrl());
	    psdto.setStock(pdto.getStock());
	    psdto.setDescription(pdto.getDescription());
	    return psdto;
	}
	
	
	//productDto list to ProductSellerDto list
	public List<ProductSellerDto> mapToProductSellerDtoList(List<ProductDto> productDtoList) {
	    List<ProductSellerDto> productSellerDtoList = new ArrayList<>();

	    for (ProductDto pdto : productDtoList) {
	        ProductSellerDto psdto = mapToProductSellerDto(pdto);
	        productSellerDtoList.add(psdto);
	    }

	    return productSellerDtoList;
	}
}

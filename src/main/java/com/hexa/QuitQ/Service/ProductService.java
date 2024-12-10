package com.hexa.QuitQ.Service;

import java.util.List;

import com.hexa.QuitQ.DTO.ProductDto;
import com.hexa.QuitQ.entities.Product;
import com.hexa.QuitQ.enums.ProductCategory;
import com.hexa.QuitQ.exception.ProductUpdateException;
import com.hexa.QuitQ.exception.ResourceNotFoundException;

public interface ProductService {
	Product createProduct(String email,ProductDto productDto) throws ResourceNotFoundException;
	List<Product> searchProductsSQL(String productName);
	public Product findProductById(Long id) throws ResourceNotFoundException;
	List<Product> findAllProducts();
	List<Product> findProductsBySellerId(Long sellerId)throws ResourceNotFoundException;
	List<Product> findProductsByBrand(String brand);
	List<Product> findProductsByCategory(ProductCategory category);
	List<Product> findProductInPriceRange(int min, int max);
	Long productsCount();
	Product updateProduct(Long product_id,ProductDto productDto)throws ResourceNotFoundException, ProductUpdateException;
	Product UpdateCategory(Long product_id, ProductCategory updatedCategory)throws ResourceNotFoundException;
	Boolean DeleteProduct(Long product_id) throws ResourceNotFoundException;
	List<Product> setOfBrandProducts(List<String> brandList);
	Boolean deleteProductsBySellerId(Long seller_id) throws ResourceNotFoundException;
}

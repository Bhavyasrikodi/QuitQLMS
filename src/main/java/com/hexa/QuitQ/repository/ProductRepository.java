package com.hexa.QuitQ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexa.QuitQ.entities.Product;
import com.hexa.QuitQ.entities.Seller;
import com.hexa.QuitQ.enums.ProductCategory;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{

	//Native SQL
			@Query(value="SELECT * FROM Products p WHERE p.product_name LIKE CONCAT('%',:productName,'%')"+
					"OR p.description LIKE  CONCAT('%',:productName,'%')",nativeQuery=true)
			List<Product> searchProductsSQL(String productName);
			
			List<Product> findBySellers(Seller seller);
			List<Product> findByBrand(String brand);
			List<Product> findByProductCategory(ProductCategory category);
			List<Product> findByPriceBetween(int minPrice, int maxPrice);

}

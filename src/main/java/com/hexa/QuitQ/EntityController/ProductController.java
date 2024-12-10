package com.hexa.QuitQ.EntityController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexa.QuitQ.DTO.ProductDto;
import com.hexa.QuitQ.DTO.ProductSellerDto;
import com.hexa.QuitQ.Service.ProductService;
import com.hexa.QuitQ.entities.Product;
import com.hexa.QuitQ.enums.ProductCategory;
import com.hexa.QuitQ.exception.ProductUpdateException;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.mapper.ProductMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins="*")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    // http://localhost:8080/api/v1/products/seller/create?email=lakshmisowmya@example.com
    @PostMapping("/seller/create")
    public ResponseEntity<?> createProduct(@RequestParam String email, @Valid @RequestBody ProductDto productDto) {
        try {
            Product product = productService.createProduct(email, productDto);
            ProductSellerDto responseDto = productMapper.mapToProductSellerDto(productMapper.mapToProductDto(product));
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        } catch (ProductUpdateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred.");
        }
    }
 // http://localhost:8080/api/v1/products/seller/create/more?email=lakshmi@gmail.com
    @PostMapping("/seller/create/more")
    public ResponseEntity<?> createMultipleProducts(@RequestParam String email, @Valid @RequestBody List<ProductDto> productDtoList) throws ResourceNotFoundException{
    	List<ProductSellerDto> psDtoList = new ArrayList<>();
    	for(ProductDto productDto : productDtoList) {
    		Product product = productService.createProduct(email, productDto);
            ProductSellerDto responseDto = productMapper.mapToProductSellerDto(productMapper.mapToProductDto(product));
            psDtoList.add(responseDto);
    	}
    	return ResponseEntity.status(HttpStatus.CREATED).body(psDtoList);
    }

    // http://localhost:8080/api/v1/products/getallproducts
    @GetMapping("/getallproducts")
    public ResponseEntity<List<ProductSellerDto>> getAllProducts() {
        List<Product> products = productService.findAllProducts();
        List<ProductSellerDto> productSellerDtos = productMapper.mapToProductSellerDtoList(productMapper.mapToProductDtoList(products));
        return ResponseEntity.ok(productSellerDtos);
    }

    // http://localhost:8080/api/v1/products/getproductbyid/2
    @GetMapping("/getproductbyid/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.findProductById(id);
            ProductSellerDto productSellerDto = productMapper.mapToProductSellerDto(productMapper.mapToProductDto(product));
            return ResponseEntity.ok(productSellerDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }

    // http://localhost:8080/api/v1/products/seller/update/1
    @PutMapping("/seller/update/{product_id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long product_id, @Valid @RequestBody ProductDto productDto) {
        try {
            Product updatedProduct = productService.updateProduct(product_id, productDto);
            ProductSellerDto responseDto = productMapper.mapToProductSellerDto(productMapper.mapToProductDto(updatedProduct));
            return ResponseEntity.ok(responseDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        } catch (ProductUpdateException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        } 
    }

    // http://localhost:8080/api/v1/products/seller/updateCategory/3?category=WOMEN_WEAR
    @PutMapping("/seller/updateCategory/{product_id}")
    public ResponseEntity<?> updateProductCategory(@PathVariable Long product_id, @RequestParam ProductCategory category) {
        try {
            Product updatedProduct = productService.UpdateCategory(product_id, category);
            ProductSellerDto responseDto = productMapper.mapToProductSellerDto(productMapper.mapToProductDto(updatedProduct));
            return ResponseEntity.ok(responseDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }

    // http://localhost:8080/api/v1/products/seller/delete/3
    @DeleteMapping("/seller/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long product_id) {
        try {
            boolean isDeleted = productService.DeleteProduct(product_id);
            if (isDeleted) {
                return ResponseEntity.ok("Product deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("Product not found.");
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }

    // http://localhost:8080/api/v1/products/getproductbybrand?brand=FashionBrand
    @GetMapping("/getproductbybrand")
    public ResponseEntity<List<ProductSellerDto>> getProductsByBrand(@RequestParam String brand) {
        List<Product> products = productService.findProductsByBrand(brand);
        List<ProductSellerDto> productSellerDtos = productMapper.mapToProductSellerDtoList(productMapper.mapToProductDtoList(products));
        return ResponseEntity.ok(productSellerDtos);
    }

    // http://localhost:8080/api/v1/products/productswithpriceRange?min=40&max=50
    @GetMapping("/productswithpriceRange")
    public ResponseEntity<List<ProductSellerDto>> getProductsInPriceRange(@RequestParam int min, @RequestParam int max) {
        List<Product> products = productService.findProductInPriceRange(min, max);
        List<ProductSellerDto> productSellerDtos = productMapper.mapToProductSellerDtoList(productMapper.mapToProductDtoList(products));
        return ResponseEntity.ok(productSellerDtos);
    }

    // http://localhost:8080/api/v1/products/getproductbycategory?category=WOMEN_WEAR
    @GetMapping("/getproductbycategory")
    public ResponseEntity<List<ProductSellerDto>> getProductsByCategory(@RequestParam ProductCategory category) {
        List<Product> products = productService.findProductsByCategory(category);
        List<ProductSellerDto> productSellerDtos = productMapper.mapToProductSellerDtoList(productMapper.mapToProductDtoList(products));
        return ResponseEntity.ok(productSellerDtos);
    }

    // http://localhost:8080/api/v1/products/getsetofproductsbybrands
    @GetMapping("/getsetofproductsbybrands")
    public ResponseEntity<List<ProductSellerDto>> getSetOfBrandProducts(@Valid @RequestBody List<String> brands) {
        List<Product> products = productService.setOfBrandProducts(brands);
        List<ProductSellerDto> productSellerDtos = productMapper.mapToProductSellerDtoList(productMapper.mapToProductDtoList(products));
        return ResponseEntity.ok(productSellerDtos);
    }


    // http://localhost:8080/api/v1/products/search?productName=casual
    @GetMapping("/search")
    public ResponseEntity<List<ProductSellerDto>> searchProductsByName(@RequestParam String productName) {
        List<Product> products = productService.searchProductsSQL(productName);
        List<ProductSellerDto> responseDtos = productMapper.mapToProductSellerDtoList(productMapper.mapToProductDtoList(products));
        return ResponseEntity.ok(responseDtos);
    }

    // http://localhost:8080/api/v1/products/seller/9
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<?> getProductsBySellerId(@PathVariable Long sellerId) {
        try {
            List<Product> products = productService.findProductsBySellerId(sellerId);
            List<ProductSellerDto> productSellerDtos = productMapper.mapToProductSellerDtoList(productMapper.mapToProductDtoList(products));
            return ResponseEntity.ok(productSellerDtos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }
}

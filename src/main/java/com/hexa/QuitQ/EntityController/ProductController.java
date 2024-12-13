package com.hexa.QuitQ.EntityController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.client.RestTemplate;

import com.hexa.QuitQ.DTO.OffersDto;
import com.hexa.QuitQ.DTO.ProductDto;
import com.hexa.QuitQ.DTO.ProductSellerDto;
import com.hexa.QuitQ.DTO.TiersDto;
import com.hexa.QuitQ.DTO.UserCouponDto;
import com.hexa.QuitQ.Service.CustomerService;
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
    private final CustomerService customerService;	
    private final ProductMapper productMapper;
    private RestTemplate restTemplate;
    @Autowired
    public ProductController(ProductService productService, CustomerService customerService, ProductMapper productMapper, RestTemplate restTemplate) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.customerService = customerService;
        this.restTemplate = restTemplate;
    }

    String partnersId = "71ba75b8-780f-4aba-964d-345aa739f35f";
    UUID partnerId = UUID.fromString(partnersId);

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
    @GetMapping("/getoffer")
    public ResponseEntity<?> getOfferPercentage(@RequestParam("userId") Long userId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID must not be null");
        }

        String partnersId = "71ba75b8-780f-4aba-964d-345aa739f35f";
        UUID partnerId = UUID.fromString(partnersId);

        try {
            String getProgramUrl = "http://localhost:8080/api/v1/lms/programs/getCurrentProgramId?partnerId={partnerId}";
            UUID programId = restTemplate.getForObject(getProgramUrl, UUID.class, partnerId);
            System.out.println(programId);

            String getTierUrl = "http://localhost:8080/api/v1/lms/users/getUserTier?userId={userId}&partnerId={partnerId}";
            TiersDto tier = restTemplate.getForObject(getTierUrl, TiersDto.class, userId, partnerId);
            UUID tierId = tier.getTierId();
            System.out.println("TierId: " + tierId);
            System.out.println("programId: " + programId);

            String getOfferUrl = "http://localhost:8080/api/v1/lms/offers/ getOfferByProgramIdAndTierId?program_id={programId}&tier_id={tierId}";
            OffersDto offer = restTemplate.getForObject(getOfferUrl, OffersDto.class, programId, tierId);
            return ResponseEntity.status(HttpStatus.OK).body(offer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
        }
    }
    
    @GetMapping("/getallactiveoffers")
    public ResponseEntity<?> getallactiveoffers() {

        try {
            String getProgramUrl = "http://localhost:8080/api/v1/lms/programs/getCurrentProgramId?partnerId={partnerId}";
            UUID programId = restTemplate.getForObject(getProgramUrl, UUID.class, partnerId);
            System.out.println(programId);

            String getOfferUrl = "http://localhost:8080/api/v1/lms/offers/getOffersByProgramId?program_id={programId}";
            OffersDto[] offerList = restTemplate.getForObject(getOfferUrl, OffersDto[].class, programId);
            List<OffersDto> offers = Arrays.asList(offerList);
            return ResponseEntity.status(HttpStatus.OK).body(offers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
        }
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

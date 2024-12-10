package com.hexa.QuitQ.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexa.QuitQ.DTO.ProductDto;
import com.hexa.QuitQ.Service.ProductService;
import com.hexa.QuitQ.entities.Product;
import com.hexa.QuitQ.entities.Seller;
import com.hexa.QuitQ.entities.User;
import com.hexa.QuitQ.enums.ProductCategory;
import com.hexa.QuitQ.exception.ProductUpdateException;
import com.hexa.QuitQ.exception.ResourceDeletionException;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.mapper.ProductMapper;
import com.hexa.QuitQ.repository.ProductRepository;
import com.hexa.QuitQ.repository.SellerRepository;
import com.hexa.QuitQ.repository.UserRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	private ProductRepository productRepository;
	private SellerRepository sellerRepository;
	private UserRepository userRepository;
	private ProductMapper productMapper;
	private SellerServiceImpl sellerServiceImpl;
	
	@Autowired
	public ProductServiceImpl(ProductRepository productRepository, SellerRepository sellerRepository, UserRepository userRepository,
			ProductMapper productMapper,SellerServiceImpl sellerServiceImpl) {
		super();
		this.productRepository = productRepository;
		this.sellerRepository = sellerRepository;
		this.productMapper = productMapper;
		this.userRepository = userRepository;
		this.sellerServiceImpl = sellerServiceImpl;
	}

	@Override
	public Product createProduct(String email, ProductDto productDto) throws ResourceNotFoundException,ProductUpdateException {
		if (this.userRepository.findByEmail(email) == null) {
	        throw new ResourceNotFoundException("user","email",email);
	    }
		
		User user = this.userRepository.findByEmail(email);		
		Seller seller = this.sellerRepository.findByUsers(user);		
		
		Product product = this.modelMapper.map(productDto, Product.class);
		product.setBrand(productDto.getBrand().toUpperCase());
		product.setSellers(seller);
		
		//setting seller's product list
		List<Product> sellerProducts = seller.getProducts();
		sellerProducts.add(product);
		seller.setProducts(sellerProducts);
		Product savedProduct = this.productRepository.save(product);
		if (product == null) {
		      throw new ProductUpdateException("Invalid product data.");
		}
		return savedProduct;
	}

	@Override
	public List<Product> searchProductsSQL(String productName) {
		return this.productRepository.searchProductsSQL(productName);
	}

	@Override
	public Product findProductById(Long id)  throws ResourceNotFoundException{
		Product product = this.productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("product","id", id));
		return product;
	}

	@Override
	public List<Product> findAllProducts() {
		return this.productRepository.findAll();
	}

	@Override
	public List<Product> findProductsBySellerId(Long sellerId) throws ResourceNotFoundException{
		Seller seller = this.sellerServiceImpl.getSellerById(sellerId);
		return this.productRepository.findBySellers(seller);
	}

	@Override
	public List<Product> findProductsByBrand(String brand) {
		return this.productRepository.findByBrand(brand.toUpperCase());
	}

	@Override
	public List<Product> findProductsByCategory(ProductCategory Category) {
		return this.productRepository.findByProductCategory(Category);
	}

	@Override
	public List<Product> findProductInPriceRange(int min, int max) {
		return this.productRepository.findByPriceBetween(min, max);
	}

	@Override
	public Long productsCount() {
		return this.productRepository.count();
	}

	@Override
    public Product updateProduct(Long product_id, ProductDto productDto) throws ResourceNotFoundException, ProductUpdateException {
        Product product = findProductById(product_id);

        try {
            product.setProduct_name(productDto.getProduct_name());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setStock(productDto.getStock());
            product.setProductCategory(productDto.getProductCategory());
            product.setBrand(productDto.getBrand().toUpperCase());
            product.setImgUrl(productDto.getImgUrl());

            return this.productRepository.save(product);
        } catch (Exception e) {
            throw new ProductUpdateException("Failed to update product with id: " + product_id);
        }
    }

	@Override
	public Product UpdateCategory(Long product_id, ProductCategory updatedCategory) throws ResourceNotFoundException{
		Product product = findProductById(product_id);
		product.setProductCategory(updatedCategory);
		 return this.productRepository.save(product);
	}


	@Override
	public Boolean DeleteProduct(Long product_id) throws ResourceNotFoundException,ResourceDeletionException{		
		if (productRepository.existsById(product_id)) {
            try {
            	this.productRepository.deleteById(product_id);
                return true;
            } catch (Exception e) {
                throw new ResourceDeletionException("Failed to delete product with id: " + product_id);
            }
        } else {
            throw new ResourceNotFoundException("Product", "id", product_id);
        }
	}

	@Override
	public List<Product> setOfBrandProducts(List<String> brandList) {
		List<Product> allBrandProducts = new ArrayList<>();
		for(String b : brandList) {
			List<Product> brandProducts = findProductsByBrand(b.toUpperCase());
			allBrandProducts.addAll(brandProducts);
		}
		return allBrandProducts;
	}

	@Override
	public Boolean deleteProductsBySellerId(Long seller_id) throws ResourceNotFoundException,ResourceDeletionException {
		List<Product> sellerProductList = findProductsBySellerId(seller_id);
		for(Product p : sellerProductList) {
			DeleteProduct(p.getProduct_id());
			sellerProductList.remove(p);
		}
		if(sellerProductList.size()==0) {
			return true;
		}
		else {
			return false;
		}
	}

}

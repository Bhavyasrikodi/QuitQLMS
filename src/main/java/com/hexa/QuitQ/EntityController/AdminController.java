package com.hexa.QuitQ.EntityController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hexa.QuitQ.DTO.AdminDto;
import com.hexa.QuitQ.DTO.AdminUserDto;
import com.hexa.QuitQ.DTO.CustomerAdminDto;
import com.hexa.QuitQ.DTO.CustomerDto;
import com.hexa.QuitQ.DTO.ProductSellerDto;
import com.hexa.QuitQ.DTO.SellerDto;
import com.hexa.QuitQ.Service.AdminService;
import com.hexa.QuitQ.Service.CustomerService;
import com.hexa.QuitQ.Service.ProductService;
import com.hexa.QuitQ.Service.SellerService;
import com.hexa.QuitQ.entities.Admin;
import com.hexa.QuitQ.entities.Customer;
import com.hexa.QuitQ.entities.Product;
import com.hexa.QuitQ.entities.Seller;
import com.hexa.QuitQ.enums.ProductCategory;
import com.hexa.QuitQ.exception.DuplicateEmailException;
import com.hexa.QuitQ.exception.ResourceDeletionException;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.mapper.AdminMapper;
import com.hexa.QuitQ.mapper.CustomerMapper;
import com.hexa.QuitQ.mapper.ProductMapper;
import com.hexa.QuitQ.mapper.SellerMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/quitq/admin")
@ResponseBody
@CrossOrigin(origins="*")
public class AdminController {
	private final AdminService adminService;
	private final CustomerService customerService;
	private final ProductService productService;
	private final SellerService sellerService;
	private SellerMapper sellerMapper;
	private ProductMapper productMapper;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	public AdminController(AdminService adminService, CustomerService customerService, SellerService sellerService,
			ProductService productService, SellerMapper sellerMapper, ProductMapper productMapper) {
		this.adminService = adminService;
		this.customerService = customerService;
		this.sellerService = sellerService;
		this.productService = productService;
		this.sellerMapper = sellerMapper;
		this.productMapper = productMapper;
	}

//	// http://localhost:8080/api/v1/quitq/admin/addadmin
//	@PostMapping("/addadmin")
//	public ResponseEntity<?> addAdmin(@Valid @RequestBody AdminUserDto adminUserDto) {
//		try {
//			Admin createdAdmin = adminService.addAdmin(adminUserDto);
//			AdminDto createdAdminDto = AdminMapper.mapToAdminDto(createdAdmin);
//
//			return ResponseEntity.status(HttpStatus.CREATED).body(createdAdminDto);
//		} catch (DuplicateEmailException e) {
//			return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
//		}
//	}

	// http://localhost:8080/api/v1/quitq/admin/getadmin/1
	@GetMapping("/getadmin/{id}")
	public ResponseEntity<?> getAdminById(@PathVariable Long id) throws ResourceNotFoundException {
		try {
			Admin admin = adminService.getAdminById(id);
			AdminDto adminDto = AdminMapper.mapToAdminDto(admin);
			return ResponseEntity.ok(adminDto);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
		}
	}
	
	@GetMapping("/email")
	public ResponseEntity<?> getAdminByEmail(@RequestParam("email") String email) throws ResourceNotFoundException {
	    try {
	        Admin admin = adminService.findAdminByEmail(email);
	        AdminDto adminDto = AdminMapper.mapToAdminDto(admin);  // Assuming you have a mapper for AdminDto
	        return ResponseEntity.ok(adminDto);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	// http://localhost:8080/api/v1/quitq/admin/updateadmin
	@PutMapping("/updateadmin")
	public ResponseEntity<?> updateAdmin(@RequestParam String email, @Valid @RequestBody AdminDto adminDto) {

		try {
			Admin updatedAdmin = this.adminService.updateAdmin(email, adminDto);
			AdminDto updatedAdminDto = AdminMapper.mapToAdminDto(updatedAdmin);
			return ResponseEntity.ok(updatedAdminDto);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
		} catch (DuplicateEmailException e) {
			return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
		}
	}

	// http://localhost:8080/api/v1/quitq/admin/getcustomers
	@GetMapping("/getcustomers")
	public ResponseEntity<List<CustomerAdminDto>> getAllCustomers() {
		List<Customer> customers = customerService.getAllCustomers();
		List<CustomerAdminDto> customerDtos = this.customerMapper.mapToCustomerAdminDto(customers);
		
		return ResponseEntity.ok(customerDtos);
	}

	// http://localhost:8080/api/v1/quitq/admin/getsellers
	@GetMapping("/getsellers")
	public ResponseEntity<Map<Long, SellerDto>> getAllSellers() {
		List<Seller> sellersList = sellerService.getAllSellers();
		List<SellerDto> sellerDtoList = sellerMapper.mapToSellerDtoList(sellersList);
		Map<Long, SellerDto> sellerDtoIdList = new HashMap<>();
		for (int i = 0; i < sellersList.size(); i++) {
			Seller seller = sellersList.get(i);
			SellerDto sellerDto = sellerDtoList.get(i);
			sellerDtoIdList.put(seller.getSellers_id(), sellerDto);
		}
		return ResponseEntity.ok(sellerDtoIdList);
	}

	// http://localhost:8080/api/v1/quitq/admin/getproducts
	@GetMapping("/getproducts")
	public ResponseEntity<Map<Long, ProductSellerDto>> getAllProducts() {
		List<Product> products = productService.findAllProducts();
		List<ProductSellerDto> productSellerDtos = productMapper
				.mapToProductSellerDtoList(productMapper.mapToProductDtoList(products));
		Map<Long, ProductSellerDto> productIdList = new HashMap<>();
		for (int i = 0; i < products.size(); i++) {
			Product product = products.get(i);
			ProductSellerDto productSellerDto = productSellerDtos.get(i);
			productIdList.put(product.getProduct_id(), productSellerDto);
		}
		return ResponseEntity.ok(productIdList);
	}

	// http://localhost:8080/api/v1/quitq/admin/updateProductCategory/{id}
	@PutMapping("/updateProductCategory/{id}")
	public ResponseEntity<?> updateProductCategory(@PathVariable Long id, @RequestParam ProductCategory category)
			throws ResourceNotFoundException {

		try {
			Product updatedProduct = productService.UpdateCategory(id, category);
			ProductSellerDto responseDto = productMapper
					.mapToProductSellerDto(productMapper.mapToProductDto(updatedProduct));
			return ResponseEntity.ok(responseDto);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
		}
	}

	// http://localhost:8080/api/v1/quitq/admin/customers/delete/{id}
	@DeleteMapping("/customers/delete/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable Long id) throws ResourceNotFoundException {
		try {
			boolean isRemoved = customerService.deleteCustomer(id);
			return ResponseEntity.ok("Deleted customer with id = " + id + " successfully!!");
			
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.OK).body("Customer not found for ID: " + id);
		} catch (ResourceDeletionException e) {
			return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
		}
	}

	// http://localhost:8080/api/v1/quitq/admin/sellers/delete/{id}
	@DeleteMapping("/sellers/delete/{id}")
	public ResponseEntity<String> deleteSellerById(@PathVariable Long id) throws ResourceNotFoundException {
		
		try {
			productService.deleteProductsBySellerId(id);
			boolean result = sellerService.deleteSellerById(id);
			return ResponseEntity.ok("Deleted seller with id = " + id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
		} catch (ResourceDeletionException e) {
			return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
		}
	}

	// http://localhost:8080/api/v1/quitq/admin/products/delete/{id}
	@DeleteMapping("/products/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws ResourceNotFoundException {
		try {
			boolean isDeleted = productService.DeleteProduct(id);
			return ResponseEntity.ok("Product deleted successfully.");
			
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
		}catch (ResourceDeletionException e) {
			return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
		}
	}
	
	// http://localhost:8080/api/v1/quiq/admin/customers/count
	@GetMapping("/customers/count")
    public ResponseEntity<Long> getCustomerCount() {
        long count = customerService.getCustomerCount();
        return ResponseEntity.ok(count);
    }

	// http://localhost:8080/api/v1/quitq/admin/sellers/count
	 @GetMapping("/sellers/count")
	 public ResponseEntity<Long> getSellersCount() {
		 long count =  sellerService.getSellersCount();
	     return ResponseEntity.ok(count);     
	 }
	 
	 // http://localhost:8080/api/v1/quitq/admin/products/count
	 @GetMapping("/products/count")
	 public ResponseEntity<Long> productsCount() {
	     Long count = productService.productsCount();
	     return ResponseEntity.ok(count);
	 }
}
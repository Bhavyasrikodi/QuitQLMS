package com.hexa.QuitQ.ServiceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexa.QuitQ.DTO.CustomerDto;
import com.hexa.QuitQ.DTO.CustomerUserDto;
import com.hexa.QuitQ.Service.CartService;
import com.hexa.QuitQ.Service.CustomerService;
import com.hexa.QuitQ.entities.Cart;
import com.hexa.QuitQ.entities.Customer;
import com.hexa.QuitQ.entities.User;
import com.hexa.QuitQ.exception.DuplicateEmailException;
import com.hexa.QuitQ.exception.ResourceDeletionException;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.mapper.CustomerUserMapper;
import com.hexa.QuitQ.repository.CartRepository;
import com.hexa.QuitQ.repository.CustomerRepository;
import com.hexa.QuitQ.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{
	@Autowired
    private CustomerRepository customerRepository;
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private CartRepository cartRepository;
	
    @Autowired
    private ModelMapper modelMapper;

    private CartService cartService;

	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public CustomerServiceImpl(CartService cartService, PasswordEncoder passwordEncoder) {
		super();
		this.cartService = cartService;
		this.passwordEncoder = passwordEncoder;
	}


    
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Override
    public Customer addCustomer(CustomerUserDto customerDto) {
            User user = CustomerUserMapper.mapToUser(customerDto);
            if (userRepository.findByEmail(user.getEmail()) != null) {
                throw new DuplicateEmailException("A user with this email already exists: " + user.getEmail());
            }
            Customer customer = new Customer();
	        customer.setPaymentMode(customerDto.getPaymentMode());
	        customer.setUsers(user);
	        customer.getUsers().setPassword(passwordEncoder.encode(customerDto.getPassword()));
	        Customer savedCustomer = customerRepository.save(customer);

	        Cart cart = cartService.createCart(savedCustomer);

	        return savedCustomer;
         
    }

    @Override
    public Customer getCustomerById(Long id) throws ResourceNotFoundException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
    }
    
    @Override
    public Customer findCustomerByEmail(String email)  throws ResourceNotFoundException{
    	if (userRepository.findByEmail(email) == null) {
            throw new ResourceNotFoundException("customer","email",email);
        }
        Customer customer =customerRepository.findCustomerByEmail(email);
        return customer;
    }
    
	@Override
	public long getCustomerCount() {
		return customerRepository.count();
	}

	@Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
	
	@Override
	public Customer updateCustomer(String email, CustomerDto customerDto) throws ResourceNotFoundException {

		if (this.userRepository.findByEmail(email) == null) {
	        throw new ResourceNotFoundException("user","email",email);
	    }
		
		if (!email.equals(customerDto.getEmail())) {
			System.out.println(97);
		    if (userRepository.findByEmail(customerDto.getEmail()) != null) {
	            throw new DuplicateEmailException("A user with this email already exists: " + customerDto.getEmail());
	        }
	    }
		
		
		
	    User EnteredUser = this.userRepository.findByEmail(email);
	    
	    Customer customer = this.customerRepository.findByUsers(EnteredUser);

	    EnteredUser.setUser_name(customerDto.getUser_name());
	    EnteredUser.setAddress(customerDto.getAddress());
	    EnteredUser.setPhone_number(customerDto.getPhone_number());
	    EnteredUser.setEmail(customerDto.getEmail());
	    
	    customer.setUsers(EnteredUser);
	    customer.setPaymentMode(customerDto.getPaymentMode());
	    
	    return this.customerRepository.save(customer);
	}


	@Override
    public boolean deleteCustomer(Long id) throws ResourceNotFoundException {
        if (customerRepository.existsById(id)) {
            try {
                customerRepository.deleteById(id);
                return true;
            } catch (Exception e) {
                throw new ResourceDeletionException("Failed to delete customer with id: " + id);
            }
        } else {
            throw new ResourceNotFoundException("Customer", "id", id);
        }
    }

    public User getUserIdByEmail(String email) {
		return userRepository.getUserIdByEmail(email);
	}

	

	

    
}
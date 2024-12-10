package com.hexa.QuitQ.ServiceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexa.QuitQ.DTO.SellerDto;
import com.hexa.QuitQ.DTO.SellerUserDto;
import com.hexa.QuitQ.Service.SellerService;
import com.hexa.QuitQ.entities.Seller;
import com.hexa.QuitQ.entities.User;
import com.hexa.QuitQ.exception.DuplicateEmailException;
import com.hexa.QuitQ.exception.ResourceDeletionException;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.repository.SellerRepository;
import com.hexa.QuitQ.repository.UserRepository;

@Service
public class SellerServiceImpl implements SellerService {

	private SellerRepository sellerRepository;
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public SellerServiceImpl(UserRepository userRepository, SellerRepository sellerRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.sellerRepository = sellerRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Seller createSeller(SellerUserDto sellerUserDto) {

		if (userRepository.findByEmail(sellerUserDto.getEmail()) != null) {
			throw new DuplicateEmailException("A user with this email already exists: " + sellerUserDto.getEmail());
		}

		Seller seller = this.modelMapper.map(sellerUserDto, Seller.class);
		User user = this.modelMapper.map(sellerUserDto, User.class);
		seller.setUsers(user);
		seller.getUsers().setPassword(passwordEncoder.encode(sellerUserDto.getPassword()));
		this.userRepository.save(user);

		return this.sellerRepository.save(seller);

	}

	@Override
	public List<Seller> getAllSellers() {
		return this.sellerRepository.findAll();
	}

	@Override
	public Seller getSellerById(Long id) throws ResourceNotFoundException {
		Seller seller = this.sellerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("seller", "id", id));
		return seller;
	}

	@Override
	public boolean deleteSellerById(Long id) throws ResourceNotFoundException {
		if (sellerRepository.existsById(id)) {
			try {
				Seller seller = this.getSellerById(id);
				Long userId = seller.getUsers().getUser_id();
				this.sellerRepository.deleteById(id);
				this.userRepository.deleteById(userId);
				return true;
			} catch (Exception e) {
				throw new ResourceDeletionException("Failed to delete customer with id: " + id);
			}
		} else {
			throw new ResourceNotFoundException("Seller", "id", id);
		}
	}
	
	@Override
	public Seller findSellerByEmail(String email) throws ResourceNotFoundException {
	    Seller seller = sellerRepository.findSellerByEmail(email);
	    if (seller == null) {
	        throw new ResourceNotFoundException("Seller not found for email: " + email);
	    }
	    return seller;
	}

	@Override
	public Long getSellersCount() {
		return this.sellerRepository.count();
	}

	@Override
	public Seller updateSeller(String email, SellerDto sellerDto) throws ResourceNotFoundException {
		if (this.userRepository.findByEmail(email) == null) {
			throw new ResourceNotFoundException("user", "email", email);
		}

		if (!email.equals(sellerDto.getEmail())) {
			System.out.println(97);
			if (userRepository.findByEmail(sellerDto.getEmail()) != null) {
				throw new DuplicateEmailException("A user with this email already exists: " + sellerDto.getEmail());
			}
		}

		User user = this.userRepository.findByEmail(email);

		// Use the updated repository method to find Seller by User object
		Seller seller = this.sellerRepository.findByUsers(user);

		// Update User details
		user.setUser_name(sellerDto.getUser_name());
		user.setAddress(sellerDto.getAddress());
		user.setPhone_number(sellerDto.getPhone_number());
		user.setEmail(sellerDto.getEmail());

		// Update Seller details
		seller.setAccount_number(sellerDto.getAccount_number());
		seller.setGST_number(sellerDto.getGST_number());
		seller.setIFSC_code(sellerDto.getIFSC_code());
		seller.setStore_name(sellerDto.getStore_name());
		seller.setShippingMode(sellerDto.getShippingMode());

		seller.setUsers(this.userRepository.save(user));
		return this.sellerRepository.save(seller);
	}

}

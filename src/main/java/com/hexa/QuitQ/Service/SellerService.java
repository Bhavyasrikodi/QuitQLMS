package com.hexa.QuitQ.Service;


import java.util.List;

import com.hexa.QuitQ.DTO.SellerDto;
import com.hexa.QuitQ.DTO.SellerUserDto;
import com.hexa.QuitQ.entities.Seller;
import com.hexa.QuitQ.exception.ResourceNotFoundException;

public interface SellerService {
	
		public Seller createSeller(SellerUserDto sellerUserDto);
		
		public List<Seller> getAllSellers();
		
		public Seller getSellerById(Long id)throws ResourceNotFoundException;
		 Seller findSellerByEmail(String email) throws ResourceNotFoundException;
		
		public boolean deleteSellerById(Long id)throws ResourceNotFoundException;
		
		public Long getSellersCount();
		
		public Seller updateSeller(String email, SellerDto sellerDto)throws ResourceNotFoundException;
		
}
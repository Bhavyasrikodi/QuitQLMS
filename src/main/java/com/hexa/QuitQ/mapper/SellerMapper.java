package com.hexa.QuitQ.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


import com.hexa.QuitQ.DTO.SellerDto;
import com.hexa.QuitQ.DTO.UserDto;
import com.hexa.QuitQ.entities.Seller;
import com.hexa.QuitQ.entities.User;

public class SellerMapper {
	@Autowired
	private ModelMapper modelmapper;
	
	public Seller mapToSeller(SellerDto sellerDto) {
		User user = this.modelmapper.map(sellerDto, User.class);
		Seller seller = this.modelmapper.map(sellerDto, Seller.class);
		seller.setUsers(user);
		return seller;
	}
	
	public SellerDto mapToSellerDto(Seller seller) {
		UserDto userDto = this.modelmapper.map(seller.getUsers(), UserDto.class);
		SellerDto sellerDto = this.modelmapper.map(seller,SellerDto.class);
		sellerDto.setUser_name(userDto.getUser_name());
		sellerDto.setUserRole(userDto.getUserRole());
		sellerDto.setAddress(userDto.getAddress());
		sellerDto.setEmail(userDto.getEmail());
		sellerDto.setPhone_number(userDto.getPhone_number());
		return sellerDto;
	}
	
	public List<SellerDto> mapToSellerDtoList(List<Seller> listOfSellers){
	List<SellerDto> dtoList = new ArrayList<>();
		for(int i=0;i<listOfSellers.size();i++) {
			SellerDto sdto = this.mapToSellerDto(listOfSellers.get(i));
			dtoList.add(sdto);
		}
	return dtoList;
	}
	
	public List<Seller> mapToProductList(List<SellerDto> dtoList){
		List<Seller> listOfSellers = new ArrayList<>();
			for(int i=0;i<dtoList.size();i++) {
				Seller seller = this.mapToSeller(dtoList.get(i));
				listOfSellers.add(seller);
			}
		return listOfSellers;
	}
	public UserDto mapToUserDto(Seller seller) {
        if (seller == null || seller.getUsers() == null) {
            return null; // or throw an exception depending on your use case
        }
        return this.modelmapper.map(seller.getUsers(), UserDto.class);
    }
}

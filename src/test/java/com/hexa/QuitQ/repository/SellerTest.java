package com.hexa.QuitQ.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexa.QuitQ.Service.OrderItemService;
import com.hexa.QuitQ.entities.OrderItem;
import com.hexa.QuitQ.entities.Seller;
import com.hexa.QuitQ.exception.ResourceNotFoundException;

@SpringBootTest
public class SellerTest {

    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private OrderItemService orderItemService;

    // 1. Test for fetching all sellers
    @Test
    public void testGetAllSellers() {
        List<Seller> sellers = sellerRepository.findAll();
        assertNotNull(sellers);  
        assertTrue(sellers.size() > 0);
        System.out.println("Number of sellers: " + sellers.size());
    }

    // 2. Test for fetching seller by ID
    @Test
    public void testGetSellerById() throws ResourceNotFoundException {
        Long sellerId = 1L; 
        Optional<Seller> seller = sellerRepository.findById(sellerId);
        assertTrue(seller.isPresent());  
        System.out.println("Seller found: " + seller.get().getUsers().getUser_name());
    }

    // 3. Test for fetching seller by email
    @Test
    public void testFindSellerByEmail() throws ResourceNotFoundException {
        String email = "lakshmi@gmail.com"; 
        Seller seller = sellerRepository.findSellerByEmail(email);
        assertNotNull(seller);  
        assertEquals(email, seller.getUsers().getEmail());
        System.out.println("Seller found with email: " + seller.getUsers().getEmail());
    }
    
    //4. Test for fetching seller's order items
    @Test
    public void testFindOrderItemsBySellerId() throws ResourceNotFoundException {
    	Long sellerId = 1L;
    	List<OrderItem> orderItemsList = orderItemService.findOrderItemsBySellerId(sellerId);
    	System.out.print(orderItemsList);
    }
}
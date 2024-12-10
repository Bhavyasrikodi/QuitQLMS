package com.hexa.QuitQ.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexa.QuitQ.Service.CartItemService;
import com.hexa.QuitQ.Service.CartService;
import com.hexa.QuitQ.entities.Cart;
import com.hexa.QuitQ.entities.Customer;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.repository.CartRepository;
import com.hexa.QuitQ.repository.CustomerRepository;

@Service
public class CartServiceImpl implements CartService{
	@Autowired
    private CartRepository cartRepository;
	@Autowired
    private CustomerRepository customerRepository;
	
private CartItemService cartItemService;
	
	@Autowired
	public CartServiceImpl(CartItemService cartItemService) {
		super();
		this.cartItemService = cartItemService;
	}
	@Override
	public Cart createCart(Customer savedCustomer) {
		Cart cart = new Cart();
        cart.setTotal_price(0.0f);
        cart.setCustomers(savedCustomer);
		return cartRepository.save(cart);
	}
	
	@Override
	public void updateCartPrice(Cart cart) {
        cart.setTotal_price(cartItemService.getTotalPrice(cart));
        cartRepository.save(cart);
    }
	@Override
	public float getCartTotalPrice(Customer customer) throws ResourceNotFoundException {
        // Retrieve customer
        
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found " );
        }

        // Retrieve cart
        Cart cart = customer.getCart();
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found for customer " );
        }

        // Return total price
        return cart.getTotal_price();
    }
	
	public Cart getCartById(Long id)throws ResourceNotFoundException {
		Cart cart = cartRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("cart", "cart_id",id));
		return cart;
	}

}
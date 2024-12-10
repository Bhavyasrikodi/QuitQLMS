package com.hexa.QuitQ.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexa.QuitQ.Service.CartItemService;
import com.hexa.QuitQ.Service.ProductService;
import com.hexa.QuitQ.entities.Cart;
import com.hexa.QuitQ.entities.CartItem;
import com.hexa.QuitQ.entities.Product;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.repository.CartItemRepository;
import com.hexa.QuitQ.repository.CartRepository;

@Service
public class CartItemServiceImpl implements CartItemService{
	
	@Autowired
    private CartItemRepository cartItemRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	private ProductService productService;
	
	@Autowired
	public CartItemServiceImpl(ProductService productService) {
		super();
		this.productService = productService;
	}
	
	public CartItem addCartItem(Cart cart, Product product, int quantity) {
	    Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);
	    
	    if (existingCartItem.isPresent()) {
	        CartItem cartItem = existingCartItem.get();
	        int newQuantity = quantity-cartItem.getCart_quantity();
	        cartItem.setCart_quantity(cartItem.getCart_quantity()+newQuantity);
	        cart.setTotal_quantity(cart.getTotal_quantity()+newQuantity);
	        cartItemRepository.save(cartItem);
	        cartRepository.save(cart);
	        return cartItem;
	    } else {
	        CartItem cartItem = new CartItem();
	        cartItem.setCart(cart);
	        cartItem.setProduct(product);
	        cartItem.setCart_quantity(quantity);
	        cart.setTotal_quantity(cart.getTotal_quantity()+quantity);
	        cartRepository.save(cart);
	        return cartItemRepository.save(cartItem);
	    }
	}
	@Override
	public CartItem updateCartItem(Cart cart, Product product, int quantity) throws ResourceNotFoundException {
	    Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);
	    
	    // Check if the item is present in the Optional
	    if (existingCartItem.isPresent()) {
	        CartItem cartItem = existingCartItem.get();
	        int newQuantity = quantity-cartItem.getCart_quantity();
	        cartItem.setCart_quantity(cartItem.getCart_quantity()+newQuantity);
	        cart.setTotal_quantity(cart.getTotal_quantity()+newQuantity);
	        cartItemRepository.save(cartItem);
	        cartRepository.save(cart);
	        return cartItem;
	    } else {
	        // Throw the exception if product is not found in the cart
	        throw new ResourceNotFoundException("Product not found in the cart");
	    }
	}

	
	public List<CartItem> getCartItemsByCart(Cart cart) {
        return cartItemRepository.findByCart(cart);
    }
	
	public int getProductCountInCart(Cart cart) {
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        return cartItems.size();  
    }
	
	public boolean isCartEmpty(Cart cart) {
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        return cartItems.isEmpty();
    }
	@Override
    public void removeProductFromCart(Cart cart, Long productId) throws ResourceNotFoundException {
        Product product = productService.findProductById(productId);
        if (product == null) {
            throw new ResourceNotFoundException("Product with ID " + productId + " not found.");
        }
        Optional<CartItem> cartItem = cartItemRepository.findByCartAndProduct(cart, product);
        cart.setTotal_quantity(cart.getTotal_quantity()-cartItem.get().getCart_quantity());
        cartRepository.save(cart);
        cartItem.ifPresent(cartItemRepository::delete);
    }
	@Override
	 public void clearCart(Cart cart) {
	        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
	        cartItemRepository.deleteAll(cartItems);
	    }
	
	public float getTotalPrice(Cart cart) {
        return cartItemRepository.findByCart(cart).stream()
                .map(cartItem -> cartItem.getProduct().getPrice() * cartItem.getCart_quantity())
                .reduce(0f, Float::sum);
    }
}
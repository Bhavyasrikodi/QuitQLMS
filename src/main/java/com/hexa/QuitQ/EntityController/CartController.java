package com.hexa.QuitQ.EntityController;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexa.QuitQ.DTO.CartDto;
import com.hexa.QuitQ.DTO.CartItemDto;
import com.hexa.QuitQ.DTO.ProductDto;
import com.hexa.QuitQ.DTO.ProductSellerDto;
import com.hexa.QuitQ.Service.CartItemService;
import com.hexa.QuitQ.Service.CartService;
import com.hexa.QuitQ.Service.CustomerService;
import com.hexa.QuitQ.Service.ProductService;
import com.hexa.QuitQ.entities.Cart;
import com.hexa.QuitQ.entities.CartItem;
import com.hexa.QuitQ.entities.Customer;
import com.hexa.QuitQ.entities.Product;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.mapper.CartItemMapper;
import com.hexa.QuitQ.mapper.CartMapper;
import com.hexa.QuitQ.mapper.ProductMapper;

@RestController
@RequestMapping("/api/v1/quitq/cart")
@ResponseBody
@CrossOrigin(origins="*")
public class CartController {

    private final CartService cartService;
    private final CartItemService cartItemService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;
    private final CartItemMapper cartItemMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public CartController(CartService cartService, CartItemService cartItemService, CustomerService customerService,
                          ProductService productService, CartMapper cartMapper, ProductMapper productMapper,
                          CartItemMapper cartItemMapper, ModelMapper modelMapper) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.customerService = customerService;
        this.productService = productService;
        this.cartMapper = cartMapper;
        this.productMapper = productMapper;
        this.cartItemMapper = cartItemMapper;
        this.modelMapper = modelMapper;
    }
    
    //http://localhost:8080/api/v1/quitq/cart/customer/2/addProduct?productId=1&quantity=5
    @PostMapping("/customer/{customerId}/addProduct")
    public ResponseEntity<?> addProductToCart(
            @PathVariable Long customerId,
            @RequestParam Long productId,
            @RequestParam int quantity) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            Cart cart = customer.getCart();
            Product product = productService.findProductById(productId);

            CartItem cartItem = cartItemService.addCartItem(cart, product, quantity);
            cartService.updateCartPrice(cart);

            CartDto cartDto = cartMapper.mapToCartDto(cart);
            return ResponseEntity.ok(cartDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    //	http://localhost:8080/api/v1/quitq/cart/customer/2/updateProductQuantity?productId=1&quantity=3
    @PutMapping("/customer/{customerId}/updateProductQuantity")
    public ResponseEntity<?> updateProductQuantityInCart(
            @PathVariable Long customerId,
            @RequestParam Long productId,
            @RequestParam int quantity) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            Cart cart = customer.getCart();
            Product product = productService.findProductById(productId);

            CartItem cartItem = cartItemService.updateCartItem(cart, product, quantity);
            cartService.updateCartPrice(cart);

            CartDto cartDto = cartMapper.mapToCartDto(cart);
            return ResponseEntity.ok(cartDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    //	http://localhost:8080/api/v1/quitq/cart/customer/2/items
    @GetMapping("/customer/{customerId}/items")
    public ResponseEntity<?> getCartItems(@PathVariable Long customerId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            Cart cart = customer.getCart();
            CartDto cartDto = cartMapper.mapToCartDto(cart);

            List<CartItem> cartItems = cartItemService.getCartItemsByCart(cart);
            List<CartItemDto> cartItemDtos = cartItems.stream()
                    .map(cartItem -> {
                        ProductSellerDto productSellerDto = productMapper.mapToProductSellerDto(productMapper.mapToProductDto(cartItem.getProduct()));
                        CartItemDto cartItemDto = this.modelMapper.map(productSellerDto, CartItemDto.class);
                        cartItemDto.setCart_quantity(cartItem.getCart_quantity());
                        return cartItemDto;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(cartDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    //	http://localhost:8080/api/v1/quitq/cart/customer/2/productCount
    @GetMapping("/customer/{customerId}/productCount")
    public ResponseEntity<?> getProductCountInCart(@PathVariable Long customerId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            Cart cart = customer.getCart();

            int productCount = cartItemService.getProductCountInCart(cart);
            return ResponseEntity.ok(productCount);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    //	http://localhost:8080/api/v1/quitq/cart/customer/2/isEmpty
    @GetMapping("/customer/{customerId}/isEmpty")
    public ResponseEntity<?> isCartEmpty(@PathVariable Long customerId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            Cart cart = customer.getCart();

            boolean isEmpty = cartItemService.isCartEmpty(cart);
            return ResponseEntity.ok(isEmpty);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    //	http://localhost:8080/api/v1/quitq/cart/customer/2/removeProduct?productId=1
    @DeleteMapping("/customer/{customerId}/removeProduct")
    public ResponseEntity<?> removeProductFromCart(
            @PathVariable Long customerId,
            @RequestParam Long productId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            Cart cart = customer.getCart();

            cartItemService.removeProductFromCart(cart, productId);
            cartService.updateCartPrice(cart);

            return ResponseEntity.ok("Product removed from cart successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    //	http://localhost:8080/api/v1/quitq/cart/customer/2/clear
    @DeleteMapping("/customer/{customerId}/clear")
    public ResponseEntity<?> clearCart(@PathVariable Long customerId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            Cart cart = customer.getCart();

            cartItemService.clearCart(cart);
            cartService.updateCartPrice(cart);

            return ResponseEntity.ok("All products removed from cart successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    //	http://localhost:8080/api/v1/quitq/cart/customer/2/totalPrice
    @GetMapping("/customer/{customerId}/totalPrice")
    public ResponseEntity<?> getCartTotalPrice(@PathVariable Long customerId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            float totalPrice = cartService.getCartTotalPrice(customer);
            return ResponseEntity.ok(totalPrice);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}

package com.hexa.QuitQ.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexa.QuitQ.DTO.OrderRequestDto;
import com.hexa.QuitQ.DTO.PaymentRequestDto;
import com.hexa.QuitQ.Service.CartItemService;
import com.hexa.QuitQ.Service.CartService;
import com.hexa.QuitQ.Service.OrderItemService;
import com.hexa.QuitQ.Service.OrderService;
import com.hexa.QuitQ.Service.PaymentService;
import com.hexa.QuitQ.Service.ProductService;
import com.hexa.QuitQ.entities.Cart;
import com.hexa.QuitQ.entities.CartItem;
import com.hexa.QuitQ.entities.Order;
import com.hexa.QuitQ.entities.Payment;
import com.hexa.QuitQ.entities.Product;
import com.hexa.QuitQ.enums.PaymentStatus;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.repository.PaymentRepository;
import com.hexa.QuitQ.repository.ProductRepository;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @Override
    public Payment createPaymentforBuyNow(PaymentRequestDto paymentRequestDto) throws ResourceNotFoundException {
        OrderRequestDto orderRequest = this.modelMapper.map(paymentRequestDto, OrderRequestDto.class);
        
        List<Long> productIds = new ArrayList<>();
        productIds.add(paymentRequestDto.getProductId());
        orderRequest.setProductIds(productIds);

        List<Integer> quantities = new ArrayList<>();
        quantities.add(paymentRequestDto.getProductQuantity());
        orderRequest.setQuantities(quantities);

        Payment payment = this.modelMapper.map(orderRequest, Payment.class);
        Order order = orderItemService.createOrderItems(orderRequest);

        payment.setOrder(order);
        payment.setPaymentModeNumber(paymentRequestDto.getPaymentModeNumber());
        payment.setPaymentMode(paymentRequestDto.getPaymentMode());
        payment.setCustomer(order.getCustomers());
        payment.setAmount(order.getTotal_order_amount());

        Payment savedPayment = paymentRepository.save(payment);
        savedPayment.setPaymentStatus(PaymentStatus.PAYMENT_SUCCESS);
        
        for(int i=0;i<productIds.size();i++) {
        	Product product = productService.findProductById(productIds.get(i));
        	product.setStock(product.getStock()-quantities.get(i));
        	productRepository.save(product);
        }
        order.setPayment(savedPayment);
        return paymentRepository.save(savedPayment);
    }

    @Override
    public Payment createPaymentforCart(PaymentRequestDto paymentRequestDto) throws ResourceNotFoundException {
        OrderRequestDto orderRequest = this.modelMapper.map(paymentRequestDto, OrderRequestDto.class);
        Cart cart = cartService.getCartById(paymentRequestDto.getCustomerId());

        List<CartItem> cartItems = cartItemService.getCartItemsByCart(cart);
        if (cartItems.isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty!! Unable to process payment.");
        }

        List<Long> productIds = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            productIds.add(product.getProduct_id());
            quantities.add(cartItem.getCart_quantity());
        }

        orderRequest.setProductIds(productIds);
        orderRequest.setQuantities(quantities);

        Payment payment = this.modelMapper.map(orderRequest, Payment.class);
        Order order = orderItemService.createOrderItems(orderRequest);

        payment.setOrder(order);
        payment.setPaymentModeNumber(paymentRequestDto.getPaymentModeNumber());
        payment.setPaymentMode(paymentRequestDto.getPaymentMode());
        payment.setCustomer(order.getCustomers());
        payment.setAmount(order.getTotal_order_amount());

        Payment savedPayment = paymentRepository.save(payment);

        if (cartItemService.isCartEmpty(cart)) {
            savedPayment.setPaymentStatus(PaymentStatus.PAYMENT_FAILED);
        } else {
            savedPayment.setPaymentStatus(PaymentStatus.PAYMENT_SUCCESS);
        }

        cartItemService.clearCart(cart);
        
        for(int i=0;i<productIds.size();i++) {
        	Product product = productService.findProductById(productIds.get(i));
        	product.setStock(product.getStock()-quantities.get(i));
        	productRepository.save(product);
        }
        order.setPayment(savedPayment);
        return paymentRepository.save(savedPayment);
    }

    @Override
    public Payment findPaymentDetails(Long orderId) throws ResourceNotFoundException {
        Order order = orderService.findOrderByid(orderId);
        Payment payment = paymentRepository.findByOrder(order);
        
        if (payment == null) {
            throw new ResourceNotFoundException("Payment", "order id", orderId);
        }
        
        return payment;
    }

    @Override
    public PaymentStatus getPaymentStatus(Long paymentId) throws ResourceNotFoundException {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new ResourceNotFoundException("Payment", "payment id", paymentId));
        
        return payment.getPaymentStatus();
    }
}

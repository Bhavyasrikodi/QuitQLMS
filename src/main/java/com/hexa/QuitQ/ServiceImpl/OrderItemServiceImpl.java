package com.hexa.QuitQ.ServiceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexa.QuitQ.DTO.OrderRequestDto;
import com.hexa.QuitQ.Service.CartService;
import com.hexa.QuitQ.Service.CustomerService;
import com.hexa.QuitQ.Service.OrderItemService;
import com.hexa.QuitQ.Service.OrderService;
import com.hexa.QuitQ.Service.ProductService;
import com.hexa.QuitQ.entities.Customer;
import com.hexa.QuitQ.entities.Order;
import com.hexa.QuitQ.entities.OrderItem;
import com.hexa.QuitQ.entities.Product;
import com.hexa.QuitQ.entities.Seller;
import com.hexa.QuitQ.enums.ItemType;
import com.hexa.QuitQ.enums.OrderStatus;
import com.hexa.QuitQ.exception.OrderCancellationException;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.repository.OrderItemRepository;
import com.hexa.QuitQ.repository.OrderRepository;
import com.hexa.QuitQ.repository.ProductRepository;
import com.hexa.QuitQ.repository.SellerRepository;

@Service
public class OrderItemServiceImpl implements OrderItemService {
	
    private ProductRepository productRepository;
	private SellerRepository sellerRepository;
	private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private OrderService orderService;
    private ModelMapper modelMapper;
    private ProductService productService;
    private CustomerService customerService;
    
	
	@Autowired
    public OrderItemServiceImpl(OrderRepository orderRepository, 
                                OrderItemRepository orderItemRepository, 
                                ProductService productService, 
                                OrderService orderService, 
                                ModelMapper modelMapper,
                                CustomerService customerService, 
                                CartService cartService,ProductRepository productRepository,SellerRepository sellerRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
        this.productService = productService;
        this.customerService = customerService;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public Order createOrderItems(OrderRequestDto orderRequest) throws ResourceNotFoundException {
        
    	Customer customer = this.customerService.getCustomerById(orderRequest.getCustomerId());
        List<Product> products = new ArrayList<>();
        for(Long product_id : orderRequest.getProductIds()) {
    		products.add(productService.findProductById(product_id));
    	}
        List<Integer> quantities = orderRequest.getQuantities();
       
        float totalAmount = 0.0f;
        int order_quantity = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int quantity = quantities.get(i);
            
            // Calculate total order quantity and total amount
            order_quantity += quantity;
            totalAmount += product.getPrice() * quantity;
            
            // Create a new OrderItem for each product
            OrderItem orderItem = new OrderItem();
            orderItem.setProducts(product);
            orderItem.setOrder_item_quantity(quantity);
            orderItem.setTotal_price(product.getPrice() * quantity);
            orderItem.setItemType(ItemType.CART);
            orderItem.setOrderStatus(OrderStatus.CONFIRMED);
            orderItem.setCart(customer.getCart());
            
            // Save the orderItem and add to list
            orderItems.add(this.orderItemRepository.save(orderItem));
        }
        Order order = this.orderService.createOrder(customer,orderItems,totalAmount,orderRequest.getShippingAddress(),order_quantity);
       List<OrderItem> savedOrderItems = new ArrayList<>();
        for(OrderItem oi: orderItems) {
        	if(orderItems.size()>1) {
        	oi.setItemType(ItemType.CART);
        	}
        	else {
        	oi.setItemType(ItemType.ORDER);
        	}
        	oi.setCart(customer.getCart());
    	    oi.setOrders(order);
    	   savedOrderItems.add(this.orderItemRepository.save(oi));
       }
        order.setOrderItems(savedOrderItems);
        return order;
    }

    @Override
    public List<OrderItem> findOrderItemsByOrderId(Long orderItemId) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", orderItemId));
        return orderItemRepository.findByOrders(order);
    }

    @Override
    public void deleteOrderItem(Long orderItemId) throws ResourceNotFoundException {
        if (!orderItemRepository.existsById(orderItemId)) {
            throw new ResourceNotFoundException("OrderItem", "orderItemId", orderItemId);
        }
        orderItemRepository.deleteById(orderItemId);
    }

    @Override
    public OrderItem findOrderItems(Long orderItemId) throws ResourceNotFoundException {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", "orderItemId", orderItemId));
    }
    
  //commented on purpose
    @Override
    public void cancelOrder(Long orderId) throws ResourceNotFoundException,OrderCancellationException {
        OrderItem order = orderItemRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order","orderItemId", orderId));
        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new OrderCancellationException("Order Item with ID " + orderId + " is already cancelled");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderItemRepository.save(order);
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus status) throws ResourceNotFoundException {
    	OrderItem order = orderItemRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order","orderItemId", orderId));
        order.setOrderStatus(status);
        orderItemRepository.save(order);
    }

    @Override
    public OrderStatus findOrderStatus(Long orderId) throws ResourceNotFoundException {
        OrderItem order = orderItemRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order","orderItemId", orderId));
        return order.getOrderStatus();
    }
    
    
@Override
    public List<OrderItem> findOrderItemsBySellerId(Long sellerId) throws ResourceNotFoundException {
        // Find the seller by sellerId
    	Seller seller = sellerRepository.findById(sellerId)
    		    .orElseThrow(() -> new ResourceNotFoundException("Seller not found with ID: " + sellerId));

        
        // Find all products associated with the seller
        List<Product> products = productRepository.findBySellers(seller);
        
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for this seller.");
        }

        // Get the order items for the products
        List<OrderItem> orderItems = orderItemRepository.findByProductsIn(products);

        if (orderItems.isEmpty()) {
            throw new ResourceNotFoundException("No order items found for this seller's products.");
        }
        
        
        return orderItems;
    }


}

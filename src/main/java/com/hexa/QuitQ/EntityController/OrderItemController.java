package com.hexa.QuitQ.EntityController;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexa.QuitQ.DTO.OrderItemDto;
import com.hexa.QuitQ.DTO.SellerOrderItemDto;
import com.hexa.QuitQ.Service.OrderItemService;
import com.hexa.QuitQ.Service.ProductService;
import com.hexa.QuitQ.entities.Order;
import com.hexa.QuitQ.entities.OrderItem;
import com.hexa.QuitQ.enums.OrderStatus;
import com.hexa.QuitQ.exception.OrderCancellationException;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.mapper.OrderItemMapper;
import com.hexa.QuitQ.mapper.OrderMapper;

@RestController
@RequestMapping("/api/v1/quitq/orderitem")
@CrossOrigin(origins="*")
public class OrderItemController {
    
	private ModelMapper modelMapper;
    private OrderItemService orderItemService;
    private OrderItemMapper orderItemMapper;
    private ProductService productService;
    private OrderMapper orderMapper;

    @Autowired
    public OrderItemController(ModelMapper modelMapper, OrderItemService orderItemService, OrderItemMapper orderItemMapper, ProductService productService, OrderMapper orderMapper) {
        this.modelMapper = modelMapper;
        this.orderItemService = orderItemService;
        this.orderItemMapper = orderItemMapper;
        this.productService = productService;
        this.orderMapper = orderMapper;
    }
    
// // Create a new order with products and quantities
//    @PostMapping("/create")
//    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequestDto orderRequest) {
//        try {
//        	Order order = orderItemService.createOrderItems(orderRequest);
//            List<OrderItem> createdOrderItemList = order.getOrderItems();
//            List<OrderItemDto> orderItemDtoList = orderItemMapper.mapToOrderItemDtos(createdOrderItemList);
//            OrderDto orderDto = orderMapper.mapToOrderDto(order);
//            orderDto.setOrderItems(orderItemDtoList);
//            return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
//        } catch (ResourceNotFoundException e) {
//            return ResponseEntity.ok(e.getMessage());
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    //http://localhost:8080/api/v1/quitq/orderitem/find/orderitems/byorderid?orderId=20
    @GetMapping("/find/orderitems/byorderid")
    public ResponseEntity<?> findOrderItemsByOrderId(@RequestParam Long orderId) {
        try {
            List<OrderItem> order = orderItemService.findOrderItemsByOrderId(orderId);
            List<OrderItemDto> orderItemDtos = this.orderItemMapper.mapToOrderItemDtos(order);
            return ResponseEntity.ok(orderItemDtos);
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    //http://localhost:8080/api/v1/quitq/orderitem/find/orderitems/byorderitemid?orderitemId=2
    @GetMapping("/find/orderitems/byorderitemid")
    public ResponseEntity<?> findOrderItems(@RequestParam Long orderitemId) {
        try {
            OrderItem orderItems = this.orderItemService.findOrderItems(orderitemId);
            OrderItemDto orderItemDtos = this.orderItemMapper.mapToOrderItemDto(orderItems);
            return ResponseEntity.ok(orderItemDtos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.ok(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    //http://localhost:8080/api/v1/quitq/orderitem/delete?orderItemId=1
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteOrderItem(@RequestParam Long orderItemId) {
        try {
            orderItemService.deleteOrderItem(orderItemId);
            
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.ok(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    
    //	http://localhost:8080/api/v1/quitq/orderitem/customer/cancel/2
    @PutMapping("/customer/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        try {
            orderItemService.cancelOrder(orderId);
            return new ResponseEntity<>("Order cancelled successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (OrderCancellationException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while canceling the order", HttpStatus.OK);
        }
    }

    // http://localhost:8080/api/v1/quitq/orderitem/status/update/3?status=SHIPPED
    @PutMapping("/status/update/{order_items_id}")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable Long order_items_id,
            @RequestParam OrderStatus status) {
        try {
            orderItemService.updateOrderStatus(order_items_id, status);
            return new ResponseEntity<>("Order status updated successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while updating the order status", HttpStatus.OK);
        }
    }

    // http://localhost:8080/api/v1/quitq/orderitem/status/get/3
    @GetMapping("/status/get/{orderId}")
    public ResponseEntity<?> findOrderStatus(@PathVariable Long orderId) {
        try {
            OrderStatus status = orderItemService.findOrderStatus(orderId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    //http://localhost:8080/api/v1/quitq/orderitem/find/orderitems/byseller?sellerId=1
    @GetMapping("/find/orderitems/byseller")
    public ResponseEntity<?> findOrderItemsBySellerId(@RequestParam Long sellerId) {
        try {
            // Fetch order items for the given seller ID
            List<OrderItem> orderItems = orderItemService.findOrderItemsBySellerId(sellerId);
            
            if (orderItems.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No order items found for the given seller ID.");
            }

            // Map OrderItems to SellerOrderItemDtos
            List<SellerOrderItemDto> sellerOrderItemDtos = orderItems.stream()
                .map(orderItem -> new SellerOrderItemDto(
                	orderItem.getOrder_items_id(),
                	orderItem.getProducts().getProduct_id(),
                    orderItem.getProducts().getProduct_name(),
                    orderItem.getProducts().getPrice(),
                    orderItem.getProducts().getDescription(),
                    orderItem.getProducts().getProductCategory(),
                    orderItem.getProducts().getImgUrl(),
                    orderItem.getProducts().getBrand(),
                    orderItem.getOrder_item_quantity(),
                    orderItem.getTotal_price(),
                    orderItem.getOrderStatus().toString(), // Convert order status to string
                    orderItem.getOrders().getCustomers().getUsers().getUser_name(),
                    orderItem.getOrders().getCustomers().getUsers().getEmail(),
                    orderItem.getOrders().getCustomers().getUsers().getPhone_number(),
                    orderItem.getOrders().getPayment().getPaymentMode().toString(),
                    orderItem.getOrders().getPayment().getPaymentStatus(),
                    orderItem.getOrders().getShipping_address(),
                    orderItem.getOrders().getOrder_date()
                    
                ))
                .collect(Collectors.toList());

            sellerOrderItemDtos.sort(Comparator.comparing(SellerOrderItemDto::getOrderDate).reversed());
            
            // Return the SellerOrderDto
            return ResponseEntity.ok(sellerOrderItemDtos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



}

package com.hexa.QuitQ.EntityController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexa.QuitQ.DTO.OrderDto;
import com.hexa.QuitQ.DTO.OrderItemDto;
import com.hexa.QuitQ.Service.OrderItemService;
import com.hexa.QuitQ.Service.OrderService;
import com.hexa.QuitQ.entities.Order;
import com.hexa.QuitQ.enums.OrderStatus;
import com.hexa.QuitQ.exception.OrderCancellationException;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.mapper.OrderItemMapper;
import com.hexa.QuitQ.mapper.OrderMapper;

@RestController
@RequestMapping("/api/v1/quitq/orders")
@CrossOrigin(origins="*")
public class OrderController {

    private OrderService orderService;
    private OrderMapper orderMapper;
    private OrderItemMapper orderItemMapper;
    private OrderItemService orderItemService;

    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper, OrderItemService orderItemService, OrderItemMapper orderItemMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.orderItemService = orderItemService;
        this.orderItemMapper = orderItemMapper;
    }

    // http://localhost:8080/api/v1/quitq/orders/customer/1
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> findOrdersOfCustomer(@PathVariable Long customerId) {
        try {
            List<Order> orders = orderService.findOrdersOfCustomer(customerId);
            List<OrderDto> orderDtos = new ArrayList<>();
            for (Order order : orders) {
                OrderDto orderDto = orderMapper.mapToOrderDto(order);
                List<OrderItemDto> orderItemDtoList = orderItemMapper.mapToOrderItemDtos(order.getOrderItems());
                orderDto.setOrderItems(orderItemDtoList);
                orderDtos.add(orderDto);
            }
            return new ResponseEntity<>(orderDtos, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.ok(e.getMessage());
        }catch (Exception e) {
            return new ResponseEntity<>("An error occurred while canceling the order", HttpStatus.OK);
        }
    }

 
}

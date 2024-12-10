package com.hexa.QuitQ.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hexa.QuitQ.DTO.CustomerUserDto;
import com.hexa.QuitQ.DTO.OrderDto;
import com.hexa.QuitQ.DTO.OrderItemDto;
import com.hexa.QuitQ.entities.Order;
import com.hexa.QuitQ.entities.OrderItem;

@Component
public class OrderMapper {
    
    private ModelMapper modelMapper;
    private CustomerUserMapper customerUserMapper;
    private OrderItemMapper orderItemMapper;
    private ProductMapper productMapper;
    
    @Autowired
    public OrderMapper(ModelMapper modelMapper,
                       CustomerUserMapper customerUserMapper,
                       OrderItemMapper orderItemMapper,
                       ProductMapper productMapper) {
        this.modelMapper = modelMapper;
        this.customerUserMapper = customerUserMapper;
        this.productMapper = productMapper;
    }

    // Custom method to map Order to OrderDto, including inner fields
    public OrderDto mapToOrderDto(Order order) {
        OrderDto orderDto = this.modelMapper.map(order, OrderDto.class);
        orderDto.setTotalOrderAmount(order.getTotal_order_amount());
        orderDto.setShippingAddress(order.getShipping_address());
        orderDto.setOrderDate(order.getOrder_date());
        orderDto.setPaymentMode(order.getPayment().getPaymentMode());
        // Map Customer details
        if (order.getCustomers() != null) {
            CustomerUserDto customerUserDto = this.customerUserMapper.mapToCustomerUserDto(order.getCustomers());
            orderDto.setUser_name(customerUserDto.getUser_name());
            orderDto.setEmail(customerUserDto.getEmail());
            orderDto.setPhone_number(customerUserDto.getPhone_number());
            
        }
        return orderDto;
    }

    // Custom method to map OrderDto to Order
    public Order mapToOrder(OrderDto orderDto) {
        Order order = new Order();
        
        // Map basic fields
        order.setTotal_order_amount(orderDto.getTotalOrderAmount());
        order.setShipping_address(orderDto.getShippingAddress());
        order.setOrder_date(orderDto.getOrderDate());

        // Mapping customer will be handled externally based on ID, so no need to map it here
        return order;
    }
}

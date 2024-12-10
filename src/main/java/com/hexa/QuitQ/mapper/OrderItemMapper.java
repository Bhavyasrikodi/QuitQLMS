package com.hexa.QuitQ.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hexa.QuitQ.DTO.CustomerUserDto;
import com.hexa.QuitQ.DTO.OrderDto;
import com.hexa.QuitQ.DTO.OrderItemDto;
import com.hexa.QuitQ.DTO.ProductDto;
import com.hexa.QuitQ.DTO.ProductSellerDto;
import com.hexa.QuitQ.entities.Order;
import com.hexa.QuitQ.entities.OrderItem;

@Component
public class OrderItemMapper {
    
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CustomerUserMapper customerUserMapper;
    @Autowired
    private ProductMapper productMapper;

    // Custom method to map OrderItem to OrderItemDto
    public OrderItemDto mapToOrderItemDto(OrderItem orderItem){
        OrderItemDto orderItemDto = this.modelMapper.map(orderItem, OrderItemDto.class);
        
        orderItemDto.setOrderItemQuantity(orderItem.getOrder_item_quantity());
        orderItemDto.setTotalPrice(orderItem.getTotal_price());
        
        if (orderItem.getProducts() != null) {
        	ProductDto productDto = this.productMapper.mapToProductDto(orderItem.getProducts());
            ProductSellerDto productSellerDto = this.productMapper.mapToProductSellerDto(productDto);
            orderItemDto.setProduct_id(productSellerDto.getProduct_id());
            orderItemDto.setProduct_name(productSellerDto.getProduct_name());
            orderItemDto.setPrice(productSellerDto.getPrice());
            orderItemDto.setDescription(productSellerDto.getDescription());
            orderItemDto.setProductCategory(productSellerDto.getProductCategory());
            orderItemDto.setImgUrl(productSellerDto.getImgUrl());
            orderItemDto.setBrand(productSellerDto.getBrand());
            orderItemDto.setStore_name(productSellerDto.getStore_name());
            orderItemDto.setAddress(productSellerDto.getAddress());
        }
        return orderItemDto;
    }

    // Custom method to map OrderItemDto to OrderItem
    public OrderItem mapToOrderItem(OrderItemDto orderItemDto){
        OrderItem orderItem = new OrderItem();

        // Map basic fields
        orderItem.setOrder_item_quantity(orderItemDto.getOrderItemQuantity());
        orderItem.setTotal_price(orderItemDto.getTotalPrice());

        // Product and Order mapping will be handled externally based on ID
        return orderItem;
    }
    
 // Convert a list of OrderItem entities to a list of OrderItemDto
    public List<OrderItemDto> mapToOrderItemDtos(List<OrderItem> orderItems) {
        return orderItems.stream().map(this::mapToOrderItemDto).collect(Collectors.toList());
    }

    // Convert a list of OrderItemDto to a list of OrderItem entities
    public List<OrderItem> mapToOrderItems(List<OrderItemDto> dtos) {
        return dtos.stream().map(this::mapToOrderItem).collect(Collectors.toList());
    }
    
 
}
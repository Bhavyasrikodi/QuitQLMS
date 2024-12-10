package com.hexa.QuitQ.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.hexa.QuitQ.DTO.OrderDto;
import com.hexa.QuitQ.DTO.OrderItemDto;
import com.hexa.QuitQ.DTO.PaymentDto;
import com.hexa.QuitQ.DTO.SellerDto;
import com.hexa.QuitQ.DTO.SellerUserDto;
import com.hexa.QuitQ.DTO.UserDto;
import com.hexa.QuitQ.entities.Order;
import com.hexa.QuitQ.entities.OrderItem;
import com.hexa.QuitQ.entities.Payment;
import com.hexa.QuitQ.entities.Seller;
import com.hexa.QuitQ.entities.User;

public class PaymentMapper {
	@Autowired
	private ModelMapper modelmapper;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	
	public PaymentDto mapToPaymentDto(Payment payment) {
		PaymentDto paymentDto = this.modelmapper.map(payment, PaymentDto.class);
	    Order order = payment.getOrder(); 
	    List<OrderItem> createdOrderItemList = order.getOrderItems();
        List<OrderItemDto> orderItemDtoList = orderItemMapper.mapToOrderItemDtos(createdOrderItemList);
        OrderDto orderDto = orderMapper.mapToOrderDto(order);
        orderDto.setOrderItems(orderItemDtoList);
        paymentDto.setTotalOrderAmount(orderDto.getTotalOrderAmount());
        paymentDto.setShippingAddress(orderDto.getShippingAddress());
        paymentDto.setOrderDate(orderDto.getOrderDate());
        paymentDto.setOrderItems(orderDto.getOrderItems());

        paymentDto.setUser_name(orderDto.getUser_name());
        paymentDto.setPhone_number(orderDto.getPhone_number());
        paymentDto.setEmail(orderDto.getEmail());

        paymentDto.setPaymentMode(orderDto.getPaymentMode());
        
	    return paymentDto;
	}
	
	public List<PaymentDto> mapToPaymentDtoList(List<Payment> listOfPayments){
	List<PaymentDto> dtoList = new ArrayList<>();
		for(int i=0;i<listOfPayments.size();i++) {
			PaymentDto sdto = this.mapToPaymentDto(listOfPayments.get(i));
			dtoList.add(sdto);
		}
	return dtoList;
	}

}
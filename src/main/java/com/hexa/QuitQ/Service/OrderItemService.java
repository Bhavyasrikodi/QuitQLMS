package com.hexa.QuitQ.Service;

import java.util.List;

import com.hexa.QuitQ.DTO.OrderRequestDto;
import com.hexa.QuitQ.entities.Order;
import com.hexa.QuitQ.entities.OrderItem;
import com.hexa.QuitQ.enums.OrderStatus;
import com.hexa.QuitQ.exception.OrderCancellationException;
import com.hexa.QuitQ.exception.ResourceNotFoundException;

public interface OrderItemService {
	List<OrderItem> findOrderItemsByOrderId(Long orderId) throws ResourceNotFoundException;

	void deleteOrderItem(Long orderItemId) throws ResourceNotFoundException;

	Order createOrderItems(OrderRequestDto orderRequest) throws ResourceNotFoundException;

	OrderItem findOrderItems(Long orderId) throws ResourceNotFoundException;

	// Cancel an order by its ID
	void cancelOrder(Long orderId) throws ResourceNotFoundException, OrderCancellationException;

	// Update the status of an order
	void updateOrderStatus(Long orderId, OrderStatus status) throws ResourceNotFoundException;

	// Find the status of a specific order
	OrderStatus findOrderStatus(Long orderId) throws ResourceNotFoundException;

	public List<OrderItem> findOrderItemsBySellerId(Long sellerId) throws ResourceNotFoundException;

}

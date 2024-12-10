package com.hexa.QuitQ.Service;

import java.util.List;

import com.hexa.QuitQ.entities.Customer;
import com.hexa.QuitQ.entities.Order;
import com.hexa.QuitQ.entities.OrderItem;
import com.hexa.QuitQ.enums.OrderStatus;
import com.hexa.QuitQ.exception.OrderCancellationException;
import com.hexa.QuitQ.exception.ResourceNotFoundException;

public interface OrderService {

//    // Find orders of a customer
	List<Order> findOrdersOfCustomer(Long customerId) throws ResourceNotFoundException;

	Order createOrder(Customer customer, List<OrderItem> orderItems, float totalAmount, String shippingAddress,
			int order_quantity);

	Order findOrderByid(Long order_Id) throws ResourceNotFoundException;
}

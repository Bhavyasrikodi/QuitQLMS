package com.hexa.QuitQ.ServiceImpl;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexa.QuitQ.Service.CustomerService;
import com.hexa.QuitQ.Service.OrderService;
import com.hexa.QuitQ.entities.Customer;
import com.hexa.QuitQ.entities.Order;
import com.hexa.QuitQ.entities.OrderItem;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.mapper.OrderMapper;
import com.hexa.QuitQ.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private CustomerService customerService;
    private OrderMapper orderMapper;
    
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerService customerService, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.orderMapper = orderMapper;
    }

    @Override
	public Order createOrder(Customer customer, List<OrderItem> orderItems, float totalAmount, String shippingAddress,int order_quantity) {
    	Order order = new Order();
        order.setShipping_address(shippingAddress);
        order.setTotal_order_amount(totalAmount);
        order.setCustomers(customer);
        order.setOrder_quantity(order_quantity);
        order = orderRepository.save(order);
        order.setOrderItems(orderItems);
        return this.orderRepository.save(order);
	}
    @Override
    public List<Order> findOrdersOfCustomer(Long customerId) throws ResourceNotFoundException {
        Customer customer = customerService.getCustomerById(customerId);
    	List<Order> orders = orderRepository.findByCustomers(customer);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Order","customerId",customerId);
        }
        orders.sort(Comparator.comparing(Order::getOrder_date).reversed());
        return orders;
    }

        
    
	    
	    @Override
	    public Order findOrderByid(Long order_Id)throws ResourceNotFoundException {
	    	Order order= orderRepository.findById(order_Id).orElseThrow(()-> new ResourceNotFoundException("order", "order_id",order_Id));
	    	return order;
	    }

	
}
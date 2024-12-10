package com.hexa.QuitQ.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexa.QuitQ.entities.Customer;
import com.hexa.QuitQ.entities.Order;

@SpringBootTest
public class CustomerTest {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private OrderRepository orderRepository;

    @Test
    void getCustomerByIdTest() {
        Long id = 1L; 
        Customer customer = customerRepository.findById(id).get();
        System.out.println("Customer ID: " + customer.getCustomer_id());
        System.out.println("Customer Name: " + customer.getUsers().getUser_name());
    }

    @Test
    void getAllCustomersTest() {
        List<Customer> customers = customerRepository.findAll();
        customers.forEach(customer -> System.out.println("Customer ID: " + customer.getCustomer_id()));
    }

    @Test
    void getCustomerByEmailTest() {
        String email = "lakshmisowmya@gmail.com"; 
        Customer customer = customerRepository.findCustomerByEmail(email);
        System.out.println("Customer ID: " + customer.getCustomer_id());
        System.out.println("Customer Email: " + customer.getUsers().getEmail());
    }

    @Test
    void getCustomerOrdersTest() {
        Long customerId = 1L; 
        Customer customer = customerRepository.findById(customerId).get();
        System.out.println("Customer ID: " + customer.getCustomer_id());
        List<Order> orders = orderRepository.findByCustomers(customer);
        System.out.print(orders);
    }

}
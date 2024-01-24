package com.example.theraven.service.interfaces;

import com.example.theraven.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer customerDetails);

    Customer deleteCustomer(Long id);
}

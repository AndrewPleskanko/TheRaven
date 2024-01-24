package com.example.theraven.service;

import com.example.theraven.entity.Customer;
import com.example.theraven.repository.CustomerRepository;
import com.example.theraven.service.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Retrieve all customers.
     *
     * @return List of customers
     */
    @Override
    public List<Customer> getAllCustomers() {
        log.info("Fetching all customers");
        return customerRepository.findAll();
    }

    /**
     * Retrieve a customer by ID.
     *
     * @param id Customer ID
     * @return Customer details
     */
    @Override
    public Customer getCustomerById(Long id) {
        log.info("Fetching customer by ID: {}", id);
        return customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found with ID: " + id));
    }

    /**
     * Create a new customer.
     *
     * @param customer Customer details
     * @return Created customer details
     */
    @Override
    public Customer createCustomer(Customer customer) {
        log.info("Creating a new customer: {}", customer);
        customer.setCreated(System.currentTimeMillis());
        customer.setUpdated(System.currentTimeMillis());
        customer.setIsActive(true);
        return customerRepository.save(customer);
    }

    /**
     * Update an existing customer.
     *
     * @param id              Customer ID
     * @param customerDetails Updated customer details
     * @return Updated customer details
     */
    @Override
    public Customer updateCustomer(Long id, Customer customerDetails) {
        log.info("Updating customer with ID: {} and details: {}", id, customerDetails);
        Customer customer = getCustomerById(id);
        customer.setFullName(customerDetails.getFullName());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhone(customerDetails.getPhone());
        customer.setUpdated(System.currentTimeMillis());
        return customerRepository.save(customer);
    }

    /**
     * Delete a customer by ID.
     *
     * @param id Customer ID
     * @return Deleted customer details
     */
    @Override
    public Customer deleteCustomer(Long id) {
        log.info("Deleting customer by ID: {}", id);
        Customer customer = getCustomerById(id);
        customer.setIsActive(false);
        return customerRepository.save(customer);
    }
}

package com.example.theraven.controller;

import com.example.theraven.entity.Customer;
import com.example.theraven.service.CustomerServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing customers.
 */
@Slf4j
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceImpl customerService;

    /**
     * Retrieve all customers.
     *
     * @return List of customers
     */
    @Operation(summary = "Retrieve all customers")
    @GetMapping
    public List<Customer> getAllCustomers() {
        log.info("Fetching all customers");
        return customerService.getAllCustomers();
    }

    /**
     * Retrieve a customer by ID.
     *
     * @param id Customer ID
     * @return Customer details
     */
    @Operation(summary = "Retrieve a customer by ID")
    @ApiResponse(responseCode = "200", description = "Customer found")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        log.info("Fetching customer by ID: {}", id);
        return customerService.getCustomerById(id);
    }

    /**
     * Create a new customer.
     *
     * @param customer Customer details
     * @return Created customer details
     */
    @Operation(summary = "Create a new customer")
    @ApiResponse(responseCode = "201", description = "Customer created successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @PostMapping
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        log.info("Creating a new customer: {}", customer);
        return customerService.createCustomer(customer);
    }

    /**
     * Update an existing customer.
     *
     * @param id              Customer ID
     * @param customerDetails Updated customer details
     * @return Updated customer details
     */
    @Operation(summary = "Update an existing customer")
    @ApiResponse(responseCode = "200", description = "Customer updated successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customerDetails) {
        log.info("Updating customer with ID: {} and details: {}", id, customerDetails);
        return customerService.updateCustomer(id, customerDetails);
    }

    /**
     * Delete a customer by ID.
     *
     * @param id Customer ID
     * @return Deleted customer details
     */
    @Operation(summary = "Delete customer by ID")
    @ApiResponse(responseCode = "200", description = "Customer deleted successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @DeleteMapping("/{id}")
    public Customer deleteCustomer(@PathVariable Long id) {
        log.info("Deleting customer by ID: {}", id);
        return customerService.deleteCustomer(id);
    }
}

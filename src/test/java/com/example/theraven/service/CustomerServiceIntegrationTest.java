package com.example.theraven.service;

import com.example.theraven.entity.Customer;
import com.example.theraven.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
public class CustomerServiceIntegrationTest {

    private Customer customer1;
    private Customer customer2;
    private List<Customer> customers;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerServiceImpl customerService;

    @Container
    private static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.jpa.generate-ddl", () -> true);
    }

    @BeforeEach
    void setUp() {
        //Given
        customer1 = new Customer(1L, 1620000000000L, 1620000000000L, "John Doe", "john.doe@example.com", "+123456789", true);
        customer2 = new Customer(2L, 1620000000000L, 1620000000000L, "Jane Doe", "jane.doe@example.com", "+987654321", true);
        customers = Arrays.asList(customer1, customer2);
    }

    @Test
    public void givenCustomers_whenGetAllCustomers_thenReturnCustomerList() {
        // given
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        // when
        List<Customer> actualCustomers = customerService.getAllCustomers();

        // then
        Assertions.assertNotNull(actualCustomers);
        assertEquals(actualCustomers.size(), 2);
        assertEquals(actualCustomers.get(0), customers.get(0));
        assertEquals(actualCustomers.get(1), customers.get(1));
    }

    // Test the getCustomerById method
    @Test
    public void givenCustomer_whenGetCustomerById_thenReturnCustomer() {
        // given
        customerRepository.save(customer1);

        // when
        Customer actualCustomer = customerService.getCustomerById(1L);

        // then
        Assertions.assertNotNull(actualCustomer);
        assertEquals(actualCustomer, customer1);
    }

    @Test
    public void givenCustomer_whenCreateCustomer_thenReturnCustomer() {
        // given
        customerRepository.save(customer1);
        // when
        Customer actualCustomer = customerService.createCustomer(customer1);

        // then
        Assertions.assertNotNull(actualCustomer);
        assertEquals(actualCustomer, customer1);
    }

    // Test the updateCustomer method
    @Test
    public void givenCustomer_whenUpdateCustomer_thenReturnCustomer() {
        // given
        customerRepository.save(customer1);
        Customer updatedCustomer = new Customer(1L, 1620000000000L, 1706100456024L, "New", "new@example.com", "+123456789", true);
        // when
        Customer actualCustomer = customerService.updateCustomer(1L, updatedCustomer);

        // then
        Assertions.assertNotNull(actualCustomer);
        assertEquals(actualCustomer.getEmail(), updatedCustomer.getEmail());
        assertEquals(actualCustomer.getPhone(), updatedCustomer.getPhone());
        assertEquals(actualCustomer.getFullName(), updatedCustomer.getFullName());
        assertEquals(actualCustomer.getIsActive(), updatedCustomer.getIsActive());
        assertNotEquals(actualCustomer.getEmail(), customer1.getEmail());
    }

    // Test the deleteCustomer method
    @Test
    public void givenCustomer_whenDeleteCustomer_thenReturnCustomer() {
        // given
        customerRepository.save(customer1);
        // when
        Customer actualCustomer = customerService.deleteCustomer(1L);

        // then
        Assertions.assertNotNull(actualCustomer);
        assertEquals(actualCustomer.getEmail(), customer1.getEmail());
        assertEquals(actualCustomer.getPhone(), customer1.getPhone());
        assertEquals(actualCustomer.getFullName(), customer1.getFullName());
        assertNotEquals(actualCustomer.getIsActive(), customer1.getIsActive());

    }
}
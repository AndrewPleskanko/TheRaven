package com.example.theraven.controller;

import com.example.theraven.entity.Customer;
import com.example.theraven.security.WebSecurityConfig;
import com.example.theraven.service.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@Import({WebSecurityConfig.class})
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerServiceImpl customerService;

    private Customer customer1;
    private Customer customer2;
    private List<Customer> customers;

    @BeforeEach
    public void setup() {
        //given
        customer1 = new Customer(1L, 1620000000000L, 1620000000000L, "John Doe", "john.doe@example.com", "+123456789", true);
        customer2 = new Customer(2L, 1620000000000L, 1620000000000L, "Jane Doe", "jane.doe@example.com", "+987654321", true);
        customers = Arrays.asList(customer1, customer2);
    }

    @Test
    @DisplayName("Get all customers should return JSON array")
    public void givenCustomers_whenGetAllCustomers_thenReturnJsonArray() throws Exception {
        // when
        when(customerService.getAllCustomers()).thenReturn(customers);
        mockMvc.perform(get("/api/customers"))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(customer1.getId()))
                .andExpect(jsonPath("$[0].fullName").value(customer1.getFullName()))
                .andExpect(jsonPath("$[0].email").value(customer1.getEmail()))
                .andExpect(jsonPath("$[0].phone").value(customer1.getPhone()))
                .andExpect(jsonPath("$[0].isActive").value(customer1.getIsActive()))
                .andExpect(jsonPath("$[0].created").value(customer1.getCreated()))
                .andExpect(jsonPath("$[0].updated").value(customer1.getUpdated()))
                .andExpect(jsonPath("$[1].id").value(customer2.getId()))
                .andExpect(jsonPath("$[1].fullName").value(customer2.getFullName()))
                .andExpect(jsonPath("$[1].email").value(customer2.getEmail()))
                .andExpect(jsonPath("$[1].phone").value(customer2.getPhone()))
                .andExpect(jsonPath("$[1].isActive").value(customer2.getIsActive()))
                .andExpect(jsonPath("$[1].created").value(customer2.getCreated()))
                .andExpect(jsonPath("$[1].updated").value(customer2.getUpdated()));

        verify(customerService).getAllCustomers();
    }


    @Test
    @DisplayName("Get customer by ID should return JSON")
    public void givenCustomer_whenGetCustomerById_thenReturnJson() throws Exception {
        // when
        when(customerService.getCustomerById(1L)).thenReturn(customer1);
        mockMvc.perform(get("/api/customers/{id}", 1L))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(customer1.getId()))
                .andExpect(jsonPath("$.fullName").value(customer1.getFullName()))
                .andExpect(jsonPath("$.email").value(customer1.getEmail()))
                .andExpect(jsonPath("$.phone").value(customer1.getPhone()))
                .andExpect(jsonPath("$.isActive").value(customer1.getIsActive()))
                .andExpect(jsonPath("$.created").value(customer1.getCreated()))
                .andExpect(jsonPath("$.updated").value(customer1.getUpdated()));
    }

    @Test
    @DisplayName("Update customer should return JSON")
    public void givenCustomer_whenUpdateCustomer_thenReturnJson() throws Exception {
        // when
        when(customerService.updateCustomer(anyLong(), any(Customer.class))).thenReturn(customer1);
        mockMvc.perform(put("/api/customers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer1)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(customer1.getId()))
                .andExpect(jsonPath("$.fullName").value(customer1.getFullName()))
                .andExpect(jsonPath("$.email").value(customer1.getEmail()))
                .andExpect(jsonPath("$.phone").value(customer1.getPhone()))
                .andExpect(jsonPath("$.isActive").value(customer1.getIsActive()))
                .andExpect(jsonPath("$.created").value(customer1.getCreated()))
                .andExpect(jsonPath("$.updated").value(customer1.getUpdated()));

        verify(customerService).updateCustomer(anyLong(), any(Customer.class));
    }

    @Test
    @DisplayName("Create customer should return JSON")
    public void givenCustomer_whenCreateCustomer_thenReturnJson() throws Exception {
        // given
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer1);

        // when
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(customer1.getId()))
                .andExpect(jsonPath("$.fullName").value(customer1.getFullName()))
                .andExpect(jsonPath("$.email").value(customer1.getEmail()))
                .andExpect(jsonPath("$.phone").value(customer1.getPhone()))
                .andExpect(jsonPath("$.isActive").value(customer1.getIsActive()))
                .andExpect(jsonPath("$.created").value(customer1.getCreated()))
                .andExpect(jsonPath("$.updated").value(customer1.getUpdated()));

        verify(customerService).createCustomer(any(Customer.class));
    }

    @Test
    @DisplayName("Delete customer should return JSON")
    public void givenCustomer_whenDeleteCustomer_thenReturnJson() throws Exception {
        // given
        when(customerService.deleteCustomer(anyLong())).thenReturn(customer1);

        // when
        mockMvc.perform(delete("/api/customers/{id}", 1L))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(customer1.getId()))
                .andExpect(jsonPath("$.fullName").value(customer1.getFullName()))
                .andExpect(jsonPath("$.email").value(customer1.getEmail()))
                .andExpect(jsonPath("$.phone").value(customer1.getPhone()))
                .andExpect(jsonPath("$.isActive").value(customer1.getIsActive()))
                .andExpect(jsonPath("$.created").value(customer1.getCreated()))
                .andExpect(jsonPath("$.updated").value(customer1.getUpdated()));

        // Verify that the service method was called
        verify(customerService).deleteCustomer(anyLong());
    }

    public static String asJsonString(Object obj) throws Exception {
        return new ObjectMapper().writeValueAsString(obj);
    }
}

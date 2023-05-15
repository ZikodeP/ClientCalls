package com.example.mockservice.controllers;

import com.example.mockservice.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
    public static final String BASE_URL = "/apiClient/v2/Customers";
    public static final String URL = "https://8d7a1c9f-95f9-4328-a121-9af8398e2578.mock.pstmn.io/Customer/";
    @Autowired
    RestTemplate restTemplate;

    @PostMapping(path= "/addCustomer", consumes = "application/json", produces = "application/json")
    public Customer addCustomer(@RequestBody Customer customer) {
        return restTemplate.postForEntity(URL, customer, Customer.class).getBody();
    }

    @GetMapping("/fetchCustomers")
    public List<Customer> fetchCustomers() {
        return restTemplate.exchange(URL, HttpMethod.GET,null, new ParameterizedTypeReference<List<Customer>>() {}).getBody();
    }

    @GetMapping("/getCustomer/{id}")
    private Customer getCustomerById(@PathVariable("id") String id) {
        return restTemplate.getForObject(URL+"/{id}", Customer.class, Map.of("id",id));
    }

    @PutMapping(path= "/updateCustomer", consumes = "application/json", produces = "application/json")
    public Customer updateCustomer(@RequestBody Customer customer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Customer> entity = new HttpEntity<>(customer,headers);

        return restTemplate.exchange(URL+customer.getId(), HttpMethod.PUT, entity, Customer.class).getBody();
    }

    @DeleteMapping(value = "/deleteCustomer/{id}")
    public String deleteProduct(@PathVariable("id") String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Customer> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(URL+id, HttpMethod.DELETE, entity, String.class).getBody();
    }
}

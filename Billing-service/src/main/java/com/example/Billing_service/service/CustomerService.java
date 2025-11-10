package com.example.Billing_service.service;



import com.example.Billing_service.entities.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerService {


    @GetMapping("/customers/{id}")
    Customer findCustomerById(@PathVariable(name = "id") Long id);
}
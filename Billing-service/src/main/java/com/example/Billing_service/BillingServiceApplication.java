package com.example.Billing_service; // Note: Adjust package name if it's com.example.Billing_service or similar

import com.example.Billing_service.entities.Bill;
import com.example.Billing_service.entities.Customer;
import com.example.Billing_service.entities.Product;
import com.example.Billing_service.entities.ProductItem;
import com.example.Billing_service.Repository.BillRepository;
import com.example.Billing_service.Repository.ProductItemRepository;
import com.example.Billing_service.service.CustomerService;
import com.example.Billing_service.service.InventoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;


import java.util.Date;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BillRepository billRepository,
                            ProductItemRepository productItemRepository,
                            CustomerService customerService,
                            InventoryService inventoryService) {
        return args -> {


            Customer c1 = customerService.findCustomerById(1L);

            System.out.println("-------------------------------------------");
            System.out.println("Customer fetched via Feign:");
            System.out.println("ID = " + c1.getId());
            System.out.println("Name = " + c1.getName());
            System.out.println("Email = " + c1.getEmail());
            System.out.println("-------------------------------------------");


            Bill bill1 = billRepository.save(new Bill(null, new Date(), c1.getId(), null, null));


            PagedModel<Product> products = inventoryService.findAllProducts();


            products.getContent().forEach(p -> {

                ProductItem productItem = new ProductItem(
                        null,
                        p.getId(),
                        null,
                        p.getPrice(),
                        90,
                        bill1
                );
                productItemRepository.save(productItem);
            });

            System.out.println("--- Database Initialized with Bill ID " + bill1.getId() + " ---");
        };
    }
}
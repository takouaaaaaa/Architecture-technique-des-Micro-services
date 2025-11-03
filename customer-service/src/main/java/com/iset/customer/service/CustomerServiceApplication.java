package com.iset.customer.service;

import com.iset.customer.service.entity.Customer;
import com.iset.customer.service.repo.CustomerRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            RepositoryRestConfiguration restConfiguration) {
        return args -> {
            restConfiguration.exposeIdsFor(Customer.class);
            customerRepository.save(new Customer("ISET", "ISET@gmail.com"));
            customerRepository.save(new Customer("ENSI", "ensi@gmail.com"));
            customerRepository.save(new Customer("ENIT", "enit@gmail.com"));
        };
    }
}
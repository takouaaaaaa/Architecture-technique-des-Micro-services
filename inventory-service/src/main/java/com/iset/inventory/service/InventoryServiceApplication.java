package com.iset.inventory.service;

import com.iset.inventory.service.entity.Product;
import com.iset.inventory.service.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository,
                            RepositoryRestConfiguration restConfiguration) {

        return args -> {
            restConfiguration.exposeIdsFor(Product.class);

            productRepository.save(new Product("PC LENOVO", 1500));
            productRepository.save(new Product("IMP EPSON", 500.5));
            productRepository.save(new Product("CEL HDMI", 3.5));

            productRepository.findAll().forEach(System.out::println);
        };
    }
}

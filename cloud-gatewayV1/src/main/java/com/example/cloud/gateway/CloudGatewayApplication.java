package com.example.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayApplication.class, args);
    }


    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()


                .route("r1", r -> r.path("/customers/**")

                        .uri("lb://CUSTOMER-SERVICE"))


                .route("r2", r -> r.path("/products/**")

                        .uri("lb://INVENTORY-SERVICE"))

                .build();
    }
}
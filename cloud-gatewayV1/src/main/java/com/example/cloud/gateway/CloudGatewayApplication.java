package com.example.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {

        final String API_KEY = "33e0841dafmsh3085ba10a5a8500p13a55djsnb7f3a05d7dd0";
        final String API_HOST = "muslimsalat.p.rapidapi.com";

        return builder.routes()


                .route("r1", r -> r.path("/customers/**")
                        .uri("lb://CUSTOMER-SERVICE"))


                .route("r2", r -> r.path("/products/**")
                        .uri("lb://INVENTORY-SERVICE"))


                .route("r3", r -> r.path("/fullBill/**")
                        .uri("lb://BILLING-SERVICE"))


                .route("r4_external_salat", r -> r.path("/muslimsalat/**")
                        .filters(f -> f

                                .rewritePath("/muslimsalat(?<remaining>.*)", "/${remaining}")


                                .addRequestHeader("x-rapidapi-key", API_KEY)
                                .addRequestHeader("x-rapidapi-host", API_HOST)


                                .circuitBreaker(config -> config
                                        .setName("muslimsalat")

                                        .setFallbackUri("forward:/defaultMuslim"))
                        )

                        .uri("https://" + API_HOST)
                )
                .build();
    }
}
package com.iset.customer.service.config;

import com.iset.customer.service.config.JwtAuthConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {


    private final JwtAuthConverter jwtAuthConverter;

    public SecurityConfiguration(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        // Disable CSRF for stateless APIs
        http.csrf(csrf -> csrf.disable());

        // Configure Authorization: Permit access to specific endpoints
        http.authorizeHttpRequests(auth -> auth
                // Allow unauthenticated access to the configuration test endpoint
                .requestMatchers("/config1").permitAll()
                // Allow unauthenticated access to actuator health and info endpoints (common practice)
                .requestMatchers("/actuator/**").permitAll()
                // All other requests must be authenticated
                .anyRequest().authenticated()
        );

        // Configure OAuth2 Resource Server to handle JWTs
        http.oauth2ResourceServer(ors ->
                ors.jwt(jwtConfigurer ->
                        jwtConfigurer.jwtAuthenticationConverter(jwtAuthConverter)
                )
        );

        // Set session creation policy to stateless for REST APIs
        http.sessionManagement(sm ->
                sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        return http.build();
    }
}
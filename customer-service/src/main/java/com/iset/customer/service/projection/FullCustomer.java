package com.iset.customer.service.projection;

import com.iset.customer.service.entity.Customer;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "fullCustomer", types = Customer.class)
public interface FullCustomer {
    Long getId();
    String getName();
    String getEmail();
}

package com.example.Billing_service.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date billingDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long customerID;

    @Transient
    private Customer customer;

    @OneToMany(mappedBy = "bill", fetch = FetchType.EAGER)
    private Collection<ProductItem> productItems;



}
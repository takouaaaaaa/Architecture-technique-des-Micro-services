package com.example.cloud.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class CircuitBreakerRestController {


    @GetMapping("/defaultMuslim")
    public Map<String, String> defaultSalat() {
        Map<String, String> data = new HashMap<>();
        data.put("message", "Horraire Salat - Service de Repli");
        data.put("Fadjer", "05:30 AM");
        data.put("Dhor", "12:30 AM");
        data.put("Asr", "03:30 AM");
        data.put("Maghreb", "06:15 PM");
        data.put("Isha", "08:00 PM");
        return data;
    }
}
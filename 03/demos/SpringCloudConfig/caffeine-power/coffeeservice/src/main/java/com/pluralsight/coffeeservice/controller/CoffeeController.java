package com.pluralsight.coffeeservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@RestController
public class CoffeeController {

    @Value("${coffee.recommendation}")
    private String recommendedCoffee;

    @GetMapping("/coffee")
    public String getCoffee() {
        return recommendedCoffee;
    }
}
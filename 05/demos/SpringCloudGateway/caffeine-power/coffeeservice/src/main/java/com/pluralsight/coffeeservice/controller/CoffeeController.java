package com.pluralsight.coffeeservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoffeeController {

    @GetMapping("/coffee")
    public String getCoffee() {
        return "double espresso";
    }
}

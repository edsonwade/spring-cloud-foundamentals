package com.pluralsight.coffeeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CoffeeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoffeeServiceApplication.class, args);
    }
}
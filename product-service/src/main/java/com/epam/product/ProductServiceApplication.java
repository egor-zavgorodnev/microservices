package com.epam.product;

import com.epam.product.validators.GroupValidator;
import com.epam.product.validators.ProductValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableHystrix
@EnableCircuitBreaker
@EnableEurekaClient
@EnableCaching
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    public Validator productValidator() {
        return new ProductValidator();
    }

    @Bean
    public Validator groupValidator() {
        return new GroupValidator();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
package com.epam.mentoring;

import com.epam.mentoring.storage.StorageProperties;
import com.epam.mentoring.storage.StorageService;
import com.epam.mentoring.validators.OrderValidator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties(StorageProperties.class)
public class EcommerceStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceStarterApplication.class, args);
    }


    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();

        };
    }

    @Bean
    public Validator orderValidator() {
        return new OrderValidator();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

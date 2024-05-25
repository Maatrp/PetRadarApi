package com.api.petradar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.api.petradar")
public class PetRadarApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetRadarApplication.class, args);
    }

}
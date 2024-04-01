package com.api.petradar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(scanBasePackages = "com.api.petradar")
public class PetRadarApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetRadarApplication.class, args);
    }

//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Bean
//    public CommandLineRunner createPasssword() {
//        return args -> {
//            System.out.println("martas: "+passwordEncoder.encode("martas"));
//            System.out.println("santis: "+passwordEncoder.encode("santis"));
//        } ;
//    }

}
package com.example.greenstoreproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GreenStoreProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreenStoreProjectApplication.class, args);
    }

}

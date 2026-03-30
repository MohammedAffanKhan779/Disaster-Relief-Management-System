package com.disaster.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application for Disaster Relief Management System
 * This application follows MVC architecture pattern with:
 * - Model: Entity classes representing database tables
 * - View: Thymeleaf templates for user interface
 * - Controller: REST controllers handling HTTP requests
 */
@SpringBootApplication
public class DisasterManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(DisasterManagementApplication.class, args);
        System.out.println("\n=================================================");
        System.out.println("Disaster Relief Management System Started!");
        System.out.println("Access the application at: http://localhost:8080");
        System.out.println("=================================================\n");
    }
}

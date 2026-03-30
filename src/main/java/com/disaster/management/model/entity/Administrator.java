package com.disaster.management.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * MODEL CLASS - Administrator Entity
 * Represents an administrator who manages users and system operations
 */
@Entity
@Table(name = "administrators")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Administrator extends User {

    // Business methods specific to Administrator
    public void manageUsers() {
        System.out.println("Managing users in the system");
    }

    public void allocateResources() {
        System.out.println("Allocating resources to relief centers");
    }

    public void generateReport() {
        System.out.println("Generating system report");
    }
}

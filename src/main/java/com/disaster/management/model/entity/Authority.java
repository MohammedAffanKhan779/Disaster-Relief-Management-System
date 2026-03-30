package com.disaster.management.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * MODEL CLASS - Authority Entity
 * Represents government or organizational authorities overseeing operations
 */
@Entity
@Table(name = "authorities")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Authority extends User {

    @Column(length = 100)
    private String organization;

    @Column(length = 100)
    private String designation;

    // Business methods specific to Authority
    public void viewReports() {
        System.out.println("Authority " + getName() + " viewing reports");
    }
}

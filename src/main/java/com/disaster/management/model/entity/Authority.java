package com.disaster.management.model.entity;

import jakarta.persistence.*;

/**
 * MODEL CLASS - Authority Entity
 * Represents government or organizational authorities overseeing operations
 */
@Entity
@Table(name = "authorities")
public class Authority extends User {

    @Column(length = 100)
    private String organization;

    @Column(length = 100)
    private String designation;

    // Constructors
    public Authority() {
        super();
    }

    public Authority(String organization, String designation) {
        super();
        this.organization = organization;
        this.designation = designation;
    }

    // Getters and Setters
    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    // Business methods specific to Authority
    public void viewReports() {
        System.out.println("Authority " + getName() + " viewing reports");
    }
}

package com.disaster.management.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * MODEL CLASS - Administrator Entity
 * Represents an administrator who manages users and system operations
 */
@Entity
@Table(name = "administrators")
public class Administrator extends User {

    @OneToMany(mappedBy = "approvedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ResourceRequest> approvedRequests = new ArrayList<>();

    @OneToMany(mappedBy = "generatedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Report> generatedReports = new ArrayList<>();

    // Constructor
    public Administrator() {
        super();
    }

    // Getters and Setters
    public List<ResourceRequest> getApprovedRequests() {
        return approvedRequests;
    }

    public void setApprovedRequests(List<ResourceRequest> approvedRequests) {
        this.approvedRequests = approvedRequests;
    }

    public List<Report> getGeneratedReports() {
        return generatedReports;
    }

    public void setGeneratedReports(List<Report> generatedReports) {
        this.generatedReports = generatedReports;
    }

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

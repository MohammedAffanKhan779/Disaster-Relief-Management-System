package com.disaster.management.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

/**
 * MODEL CLASS - Donor Entity
 * Represents a donor who contributes resources to the relief efforts
 */
@Entity
@Table(name = "donors")
public class Donor extends User {

    @NotBlank(message = "Donor type is required")
    @Column(name = "donor_type", length = 50)
    private String donorType; // Individual, Organization, Corporate, etc.

    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Donation> donations = new ArrayList<>();

    // Constructors
    public Donor() {
        super();
    }

    public Donor(String donorType) {
        super();
        this.donorType = donorType;
    }

    // Getters and Setters
    public String getDonorType() {
        return donorType;
    }

    public void setDonorType(String donorType) {
        this.donorType = donorType;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    // Business methods specific to Donor
    public void donateMoney() {
        System.out.println("Donor " + getName() + " made a monetary donation");
    }

    public void donateResource() {
        System.out.println("Donor " + getName() + " donated resources");
    }

    public void viewDonationHistory() {
        System.out.println("Viewing donation history for donor: " + getName());
    }
}

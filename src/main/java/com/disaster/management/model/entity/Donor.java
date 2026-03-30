package com.disaster.management.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * MODEL CLASS - Donor Entity
 * Represents a donor who contributes resources to the relief efforts
 */
@Entity
@Table(name = "donors")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Donor extends User {

    @NotBlank(message = "Donor type is required")
    @Column(name = "donor_type", length = 50)
    private String donorType; // Individual, Organization, Corporate, etc.

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

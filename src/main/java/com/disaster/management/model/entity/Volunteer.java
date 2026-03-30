package com.disaster.management.model.entity;

import com.disaster.management.model.enums.AvailabilityStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * MODEL CLASS - Volunteer Entity
 * Represents a volunteer who assists in disaster relief activities
 */
@Entity
@Table(name = "volunteers")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Volunteer extends User {

    @NotBlank(message = "Skill set is required")
    @Column(name = "skill_set", length = 200)
    private String skillSet;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability", length = 20)
    private AvailabilityStatus availability;

    // Business methods specific to Volunteer
    public void updateAvailability(AvailabilityStatus newStatus) {
        this.availability = newStatus;
        System.out.println("Volunteer " + getName() + " availability updated to: " + newStatus);
    }

    public void viewAssignedTasks() {
        System.out.println("Viewing assigned tasks for volunteer: " + getName());
    }
}

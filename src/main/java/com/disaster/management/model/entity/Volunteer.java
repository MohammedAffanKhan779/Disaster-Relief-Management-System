package com.disaster.management.model.entity;

import com.disaster.management.model.enums.AvailabilityStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * MODEL CLASS - Volunteer Entity
 * Represents a volunteer who assists in disaster relief activities
 */
@Entity
@Table(name = "volunteers")
public class Volunteer extends User {

    @NotBlank(message = "Skill set is required")
    @Column(name = "skill_set", length = 200)
    private String skillSet;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability", length = 20)
    private AvailabilityStatus availability;

    // Constructors
    public Volunteer() {
        super();
    }

    public Volunteer(String skillSet, AvailabilityStatus availability) {
        super();
        this.skillSet = skillSet;
        this.availability = availability;
    }

    // Getters and Setters
    public String getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(String skillSet) {
        this.skillSet = skillSet;
    }

    public AvailabilityStatus getAvailability() {
        return availability;
    }

    public void setAvailability(AvailabilityStatus availability) {
        this.availability = availability;
    }

    // Business methods specific to Volunteer
    public void updateAvailability(AvailabilityStatus newStatus) {
        this.availability = newStatus;
        System.out.println("Volunteer " + getName() + " availability updated to: " + newStatus);
    }

    public void viewAssignedTasks() {
        System.out.println("Viewing assigned tasks for volunteer: " + getName());
    }
}

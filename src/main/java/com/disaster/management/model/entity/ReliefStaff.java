package com.disaster.management.model.entity;

import jakarta.persistence.*;

/**
 * MODEL CLASS - ReliefStaff Entity
 * Represents staff members working at relief centers
 */
@Entity
@Table(name = "relief_staff")
public class ReliefStaff extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private ReliefCenter reliefCenter;

    @Column(length = 100)
    private String designation;

    // Constructors
    public ReliefStaff() {
        super();
    }

    public ReliefStaff(ReliefCenter reliefCenter, String designation) {
        super();
        this.reliefCenter = reliefCenter;
        this.designation = designation;
    }

    // Getters and Setters
    public ReliefCenter getReliefCenter() {
        return reliefCenter;
    }

    public void setReliefCenter(ReliefCenter reliefCenter) {
        this.reliefCenter = reliefCenter;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    // Business methods specific to ReliefStaff
    public void requestResources() {
        System.out.println("Relief staff " + getName() + " requesting resources");
    }

    public void updateInventory() {
        System.out.println("Relief staff " + getName() + " updating inventory");
    }

    public void trackDistribution() {
        System.out.println("Relief staff " + getName() + " tracking resource distribution");
    }
}

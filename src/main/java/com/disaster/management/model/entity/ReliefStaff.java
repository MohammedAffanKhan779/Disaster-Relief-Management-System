package com.disaster.management.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * MODEL CLASS - ReliefStaff Entity
 * Represents staff members working at relief centers
 */
@Entity
@Table(name = "relief_staff")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ReliefStaff extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private ReliefCenter reliefCenter;

    @Column(length = 100)
    private String designation;

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

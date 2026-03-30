package com.disaster.management.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MODEL CLASS - ReliefCenter Entity
 * Represents a relief center managing disaster operations
 */
@Entity
@Table(name = "relief_centers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReliefCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "center_id")
    private Integer centerId;

    @NotBlank(message = "Center name is required")
    @Column(nullable = false, length = 150)
    private String name;

    @NotBlank(message = "Location is required")
    @Column(nullable = false, length = 200)
    private String location;

    @NotNull(message = "Capacity is required")
    @Column(nullable = false)
    private Integer capacity;

    @Column(length = 500)
    private String description;

    // Business methods
    public void updateInventory() {
        System.out.println("Inventory updated for relief center: " + name);
    }

    public void requestResources() {
        System.out.println("Resources requested by relief center: " + name);
    }
}

package com.disaster.management.model.entity;

import com.disaster.management.model.enums.DonationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MODEL CLASS - Resource Entity
 * Represents a resource available for disaster relief
 */
@Entity
@Table(name = "resources")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id")
    private Integer resourceId;

    @NotBlank(message = "Resource name is required")
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull(message = "Quantity is required")
    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Resource type is required")
    @Column(nullable = false, length = 20)
    private DonationType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private ReliefCenter reliefCenter;

    @Column(length = 500)
    private String description;

    // Business method
    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
        System.out.println("Resource " + name + " quantity updated to: " + newQuantity);
    }
}

package com.disaster.management.model.entity;

import com.disaster.management.model.enums.AllocationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * MODEL CLASS - Allocation Entity
 * Represents the allocation of resources to a relief center or disaster event
 */
@Entity
@Table(name = "allocations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Allocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "allocation_id")
    private Integer allocationId;

    @NotNull(message = "Allocation date is required")
    @Column(name = "allocation_date", nullable = false)
    private LocalDate allocationDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    @Column(nullable = false, length = 20)
    private AllocationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", nullable = false)
    private ReliefCenter reliefCenter;

    @NotNull(message = "Allocated quantity is required")
    @Column(name = "allocated_quantity", nullable = false)
    private Integer allocatedQuantity;

    @Column(length = 500)
    private String notes;

    // Business methods
    public void allocateResource() {
        this.status = AllocationStatus.SCHEDULED;
        System.out.println("Resource allocated: " + resource.getName() + 
                         " Quantity: " + allocatedQuantity);
    }

    public void trackAllocation() {
        System.out.println("Tracking allocation status: " + status);
    }
}

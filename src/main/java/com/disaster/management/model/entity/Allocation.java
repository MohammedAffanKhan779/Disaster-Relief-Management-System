package com.disaster.management.model.entity;

import com.disaster.management.model.enums.AllocationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * MODEL CLASS - Allocation Entity
 * Represents the allocation of resources to a relief center or disaster event
 */
@Entity
@Table(name = "allocations")
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private ResourceRequest resourceRequest;

    @NotNull(message = "Allocated quantity is required")
    @Column(name = "allocated_quantity", nullable = false)
    private Integer allocatedQuantity;

    @Column(length = 500)
    private String notes;

    // Constructors
    public Allocation() {
    }

    public Allocation(Integer allocationId, LocalDate allocationDate, AllocationStatus status,
                      Resource resource, ReliefCenter reliefCenter, Integer allocatedQuantity, String notes) {
        this.allocationId = allocationId;
        this.allocationDate = allocationDate;
        this.status = status;
        this.resource = resource;
        this.reliefCenter = reliefCenter;
        this.allocatedQuantity = allocatedQuantity;
        this.notes = notes;
    }

    // Getters and Setters
    public Integer getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(Integer allocationId) {
        this.allocationId = allocationId;
    }

    public LocalDate getAllocationDate() {
        return allocationDate;
    }

    public void setAllocationDate(LocalDate allocationDate) {
        this.allocationDate = allocationDate;
    }

    public AllocationStatus getStatus() {
        return status;
    }

    public void setStatus(AllocationStatus status) {
        this.status = status;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public ReliefCenter getReliefCenter() {
        return reliefCenter;
    }

    public void setReliefCenter(ReliefCenter reliefCenter) {
        this.reliefCenter = reliefCenter;
    }

    public ResourceRequest getResourceRequest() {
        return resourceRequest;
    }

    public void setResourceRequest(ResourceRequest resourceRequest) {
        this.resourceRequest = resourceRequest;
    }

    public Integer getAllocatedQuantity() {
        return allocatedQuantity;
    }

    public void setAllocatedQuantity(Integer allocatedQuantity) {
        this.allocatedQuantity = allocatedQuantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

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

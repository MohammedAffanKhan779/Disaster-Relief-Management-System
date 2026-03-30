package com.disaster.management.model.entity;

import com.disaster.management.model.enums.DonationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * MODEL CLASS - Resource Entity
 * Represents a resource available for disaster relief
 */
@Entity
@Table(name = "resources")
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

    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ResourceRequest> resourceRequests = new ArrayList<>();

    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Allocation> allocations = new ArrayList<>();

    // Constructors
    public Resource() {
    }

    public Resource(Integer resourceId, String name, Integer quantity, DonationType type,
                    ReliefCenter reliefCenter, String description) {
        this.resourceId = resourceId;
        this.name = name;
        this.quantity = quantity;
        this.type = type;
        this.reliefCenter = reliefCenter;
        this.description = description;
    }

    // Getters and Setters
    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public DonationType getType() {
        return type;
    }

    public void setType(DonationType type) {
        this.type = type;
    }

    public ReliefCenter getReliefCenter() {
        return reliefCenter;
    }

    public void setReliefCenter(ReliefCenter reliefCenter) {
        this.reliefCenter = reliefCenter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ResourceRequest> getResourceRequests() {
        return resourceRequests;
    }

    public void setResourceRequests(List<ResourceRequest> resourceRequests) {
        this.resourceRequests = resourceRequests;
    }

    public List<Allocation> getAllocations() {
        return allocations;
    }

    public void setAllocations(List<Allocation> allocations) {
        this.allocations = allocations;
    }

    // Business method
    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
        System.out.println("Resource " + name + " quantity updated to: " + newQuantity);
    }
}

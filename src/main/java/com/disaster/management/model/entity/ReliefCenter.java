package com.disaster.management.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * MODEL CLASS - ReliefCenter Entity
 * Represents a relief center managing disaster operations
 */
@Entity
@Table(name = "relief_centers")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private DisasterEvent disasterEvent;

    @OneToMany(mappedBy = "reliefCenter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ReliefStaff> reliefStaff = new ArrayList<>();

    @OneToMany(mappedBy = "reliefCenter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Resource> resources = new ArrayList<>();

    @OneToMany(mappedBy = "reliefCenter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ResourceRequest> resourceRequests = new ArrayList<>();

    @OneToMany(mappedBy = "reliefCenter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Allocation> allocations = new ArrayList<>();

    // Constructors
    public ReliefCenter() {
    }

    public ReliefCenter(Integer centerId, String name, String location, Integer capacity, String description) {
        this.centerId = centerId;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.description = description;
    }

    // Getters and Setters
    public Integer getCenterId() {
        return centerId;
    }

    public void setCenterId(Integer centerId) {
        this.centerId = centerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DisasterEvent getDisasterEvent() {
        return disasterEvent;
    }

    public void setDisasterEvent(DisasterEvent disasterEvent) {
        this.disasterEvent = disasterEvent;
    }

    public List<ReliefStaff> getReliefStaff() {
        return reliefStaff;
    }

    public void setReliefStaff(List<ReliefStaff> reliefStaff) {
        this.reliefStaff = reliefStaff;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
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

    // Business methods
    public void updateInventory() {
        System.out.println("Inventory updated for relief center: " + name);
    }

    public void requestResources() {
        System.out.println("Resources requested by relief center: " + name);
    }
}

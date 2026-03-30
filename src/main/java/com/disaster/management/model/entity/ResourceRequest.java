package com.disaster.management.model.entity;

import com.disaster.management.model.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * MODEL CLASS - ResourceRequest Entity
 * Represents a request for resources from a relief center
 */
@Entity
@Table(name = "resource_requests")
public class ResourceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Integer requestId;

    @NotNull(message = "Request date is required")
    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    @Column(nullable = false, length = 20)
    private RequestStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", nullable = false)
    private ReliefCenter reliefCenter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private Administrator approvedBy;

    @OneToOne(mappedBy = "resourceRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Allocation allocation;

    @NotNull(message = "Requested quantity is required")
    @Column(name = "requested_quantity", nullable = false)
    private Integer requestedQuantity;

    @Column(length = 500)
    private String description;

    // Constructors
    public ResourceRequest() {
    }

    public ResourceRequest(Integer requestId, LocalDate requestDate, RequestStatus status,
                           ReliefCenter reliefCenter, Resource resource, Integer requestedQuantity,
                           String description) {
        this.requestId = requestId;
        this.requestDate = requestDate;
        this.status = status;
        this.reliefCenter = reliefCenter;
        this.resource = resource;
        this.requestedQuantity = requestedQuantity;
        this.description = description;
    }

    // Getters and Setters
    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public ReliefCenter getReliefCenter() {
        return reliefCenter;
    }

    public void setReliefCenter(ReliefCenter reliefCenter) {
        this.reliefCenter = reliefCenter;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Administrator getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Administrator approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Allocation getAllocation() {
        return allocation;
    }

    public void setAllocation(Allocation allocation) {
        this.allocation = allocation;
    }

    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(Integer requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Business methods
    public void submitRequest() {
        this.status = RequestStatus.PENDING;
        System.out.println("Resource request submitted for: " + resource.getName());
    }

    public void approveRequest() {
        this.status = RequestStatus.APPROVED;
        System.out.println("Resource request approved");
    }
}

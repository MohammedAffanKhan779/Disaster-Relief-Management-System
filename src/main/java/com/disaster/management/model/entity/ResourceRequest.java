package com.disaster.management.model.entity;

import com.disaster.management.model.enums.RequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * MODEL CLASS - ResourceRequest Entity
 * Represents a request for resources from a relief center
 */
@Entity
@Table(name = "resource_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @NotNull(message = "Requested quantity is required")
    @Column(name = "requested_quantity", nullable = false)
    private Integer requestedQuantity;

    @Column(length = 500)
    private String description;

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

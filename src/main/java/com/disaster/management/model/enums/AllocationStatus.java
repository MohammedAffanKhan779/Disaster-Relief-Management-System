package com.disaster.management.model.enums;

/**
 * Enumeration for Allocation Status
 * Tracks the status of resource allocation
 */
public enum AllocationStatus {
    SCHEDULED,      // Allocation is scheduled
    IN_TRANSIT,     // Resources are being transported
    DELIVERED,      // Resources have been delivered
    CANCELLED       // Allocation has been cancelled
}

package com.disaster.management.model.enums;

/**
 * Enumeration for Resource Request Status
 * Tracks the status of resource requests
 */
public enum RequestStatus {
    PENDING,        // Request submitted, awaiting review
    APPROVED,       // Request has been approved
    REJECTED,       // Request has been rejected
    FULFILLED       // Request has been fulfilled
}

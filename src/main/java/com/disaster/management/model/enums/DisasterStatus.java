package com.disaster.management.model.enums;

/**
 * Enumeration for Disaster Status
 * Tracks the current state of a disaster event
 */
public enum DisasterStatus {
    REPORTED,       // Disaster has been reported
    ACTIVE,         // Disaster is currently active
    UNDER_CONTROL,  // Disaster is being managed
    RESOLVED,       // Disaster has been resolved
    CLOSED          // Disaster case is closed
}

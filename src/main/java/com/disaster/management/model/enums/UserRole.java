package com.disaster.management.model.enums;

/**
 * Enumeration for User Roles in the Disaster Management System
 * Defines the different types of users who can access the system
 */
public enum UserRole {
    ADMIN,          // System administrator with full access
    DONOR,          // User who makes donations
    VOLUNTEER,      // User who volunteers for relief activities
    RELIEF_STAFF,   // Staff member managing relief operations
    AUTHORITY       // Government or organizational authority
}

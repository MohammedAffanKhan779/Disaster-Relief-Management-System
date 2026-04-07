package com.disaster.management.patterns.factory;

import com.disaster.management.model.entity.Administrator;
import com.disaster.management.model.entity.Authority;
import com.disaster.management.model.entity.Donor;
import com.disaster.management.model.entity.ReliefStaff;
import com.disaster.management.model.entity.User;
import com.disaster.management.model.entity.Volunteer;
import com.disaster.management.model.enums.UserRole;

/**
 * Factory pattern for creating the correct User subtype from a role.
 */
public final class UserFactory {

    private UserFactory() {
    }

    public static User createUser(UserRole role) {
        return switch (role) {
            case ADMIN -> new Administrator();
            case DONOR -> new Donor();
            case VOLUNTEER -> new Volunteer();
            case RELIEF_STAFF -> new ReliefStaff();
            case AUTHORITY -> new Authority();
        };
    }
}

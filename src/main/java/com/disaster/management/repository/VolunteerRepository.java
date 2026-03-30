package com.disaster.management.repository;

import com.disaster.management.model.entity.Volunteer;
import com.disaster.management.model.enums.AvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * REPOSITORY INTERFACE - Volunteer Repository
 * Data Access Layer for Volunteer entity
 */
@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {
    
    List<Volunteer> findByAvailability(AvailabilityStatus availability);
    
    List<Volunteer> findBySkillSetContainingIgnoreCase(String skill);
}

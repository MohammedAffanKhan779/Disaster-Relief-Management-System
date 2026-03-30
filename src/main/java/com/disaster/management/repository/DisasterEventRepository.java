package com.disaster.management.repository;

import com.disaster.management.model.entity.DisasterEvent;
import com.disaster.management.model.enums.DisasterStatus;
import com.disaster.management.model.enums.DisasterType;
import com.disaster.management.model.enums.SeverityLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * REPOSITORY INTERFACE - DisasterEvent Repository
 * Data Access Layer for DisasterEvent entity
 */
@Repository
public interface DisasterEventRepository extends JpaRepository<DisasterEvent, Integer> {
    
    List<DisasterEvent> findByStatus(DisasterStatus status);
    
    List<DisasterEvent> findByType(DisasterType type);
    
    List<DisasterEvent> findBySeverity(SeverityLevel severity);
    
    List<DisasterEvent> findByLocation(String location);
    
    List<DisasterEvent> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<DisasterEvent> findByStatusAndSeverity(DisasterStatus status, SeverityLevel severity);
}

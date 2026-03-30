package com.disaster.management.service;

import com.disaster.management.model.entity.DisasterEvent;
import com.disaster.management.model.enums.DisasterStatus;
import com.disaster.management.model.enums.DisasterType;
import com.disaster.management.model.enums.SeverityLevel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * SERVICE INTERFACE - DisasterEvent Service
 * Defines business logic operations for Disaster Event management
 */
public interface DisasterEventService {
    
    DisasterEvent saveDisasterEvent(DisasterEvent disasterEvent);
    
    Optional<DisasterEvent> getDisasterEventById(Integer id);
    
    List<DisasterEvent> getAllDisasterEvents();
    
    DisasterEvent updateDisasterEvent(DisasterEvent disasterEvent);
    
    void deleteDisasterEvent(Integer id);
    
    List<DisasterEvent> getEventsByStatus(DisasterStatus status);
    
    List<DisasterEvent> getEventsByType(DisasterType type);
    
    List<DisasterEvent> getEventsBySeverity(SeverityLevel severity);
    
    List<DisasterEvent> getEventsByLocation(String location);
    
    List<DisasterEvent> getEventsByDateRange(LocalDate startDate, LocalDate endDate);
}

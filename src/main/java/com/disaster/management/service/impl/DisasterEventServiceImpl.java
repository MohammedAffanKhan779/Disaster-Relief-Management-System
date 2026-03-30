package com.disaster.management.service.impl;

import com.disaster.management.model.entity.DisasterEvent;
import com.disaster.management.model.enums.DisasterStatus;
import com.disaster.management.model.enums.DisasterType;
import com.disaster.management.model.enums.SeverityLevel;
import com.disaster.management.repository.DisasterEventRepository;
import com.disaster.management.service.DisasterEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * SERVICE IMPLEMENTATION - DisasterEvent Service
 * Implements business logic for Disaster Event management
 */
@Service
@Transactional
public class DisasterEventServiceImpl implements DisasterEventService {

    @Autowired
    private DisasterEventRepository disasterEventRepository;

    @Override
    public DisasterEvent saveDisasterEvent(DisasterEvent disasterEvent) {
        return disasterEventRepository.save(disasterEvent);
    }

    @Override
    public Optional<DisasterEvent> getDisasterEventById(Integer id) {
        return disasterEventRepository.findById(id);
    }

    @Override
    public List<DisasterEvent> getAllDisasterEvents() {
        return disasterEventRepository.findAll();
    }

    @Override
    public DisasterEvent updateDisasterEvent(DisasterEvent disasterEvent) {
        return disasterEventRepository.save(disasterEvent);
    }

    @Override
    public void deleteDisasterEvent(Integer id) {
        disasterEventRepository.deleteById(id);
    }

    @Override
    public List<DisasterEvent> getEventsByStatus(DisasterStatus status) {
        return disasterEventRepository.findByStatus(status);
    }

    @Override
    public List<DisasterEvent> getEventsByType(DisasterType type) {
        return disasterEventRepository.findByType(type);
    }

    @Override
    public List<DisasterEvent> getEventsBySeverity(SeverityLevel severity) {
        return disasterEventRepository.findBySeverity(severity);
    }

    @Override
    public List<DisasterEvent> getEventsByLocation(String location) {
        return disasterEventRepository.findByLocation(location);
    }

    @Override
    public List<DisasterEvent> getEventsByDateRange(LocalDate startDate, LocalDate endDate) {
        return disasterEventRepository.findByDateBetween(startDate, endDate);
    }
}

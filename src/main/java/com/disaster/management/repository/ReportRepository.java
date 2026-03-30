package com.disaster.management.repository;

import com.disaster.management.model.entity.Report;
import com.disaster.management.model.entity.DisasterEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * REPOSITORY INTERFACE - Report Repository
 * Data Access Layer for Report entity
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    
    List<Report> findByDisasterEvent(DisasterEvent disasterEvent);
    
    List<Report> findByReportType(String reportType);
    
    List<Report> findByGeneratedDateBetween(LocalDate startDate, LocalDate endDate);
}

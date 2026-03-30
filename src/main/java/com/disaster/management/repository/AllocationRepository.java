package com.disaster.management.repository;

import com.disaster.management.model.entity.Allocation;
import com.disaster.management.model.entity.ReliefCenter;
import com.disaster.management.model.enums.AllocationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * REPOSITORY INTERFACE - Allocation Repository
 * Data Access Layer for Allocation entity
 */
@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Integer> {
    
    List<Allocation> findByStatus(AllocationStatus status);
    
    List<Allocation> findByReliefCenter(ReliefCenter reliefCenter);
    
    List<Allocation> findByAllocationDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<Allocation> findByReliefCenterAndStatus(ReliefCenter reliefCenter, AllocationStatus status);
}

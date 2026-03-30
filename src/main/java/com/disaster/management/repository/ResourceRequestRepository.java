package com.disaster.management.repository;

import com.disaster.management.model.entity.ResourceRequest;
import com.disaster.management.model.entity.ReliefCenter;
import com.disaster.management.model.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * REPOSITORY INTERFACE - ResourceRequest Repository
 * Data Access Layer for ResourceRequest entity
 */
@Repository
public interface ResourceRequestRepository extends JpaRepository<ResourceRequest, Integer> {
    
    List<ResourceRequest> findByStatus(RequestStatus status);
    
    List<ResourceRequest> findByReliefCenter(ReliefCenter reliefCenter);
    
    List<ResourceRequest> findByRequestDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<ResourceRequest> findByReliefCenterAndStatus(ReliefCenter reliefCenter, RequestStatus status);
}

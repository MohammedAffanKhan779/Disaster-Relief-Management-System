package com.disaster.management.repository;

import com.disaster.management.model.entity.ReliefCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * REPOSITORY INTERFACE - ReliefCenter Repository
 * Data Access Layer for ReliefCenter entity
 */
@Repository
public interface ReliefCenterRepository extends JpaRepository<ReliefCenter, Integer> {
    
    List<ReliefCenter> findByLocation(String location);
    
    List<ReliefCenter> findByCapacityGreaterThanEqual(Integer capacity);
    
    List<ReliefCenter> findByNameContainingIgnoreCase(String name);
}

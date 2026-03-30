package com.disaster.management.repository;

import com.disaster.management.model.entity.Resource;
import com.disaster.management.model.entity.ReliefCenter;
import com.disaster.management.model.enums.DonationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * REPOSITORY INTERFACE - Resource Repository
 * Data Access Layer for Resource entity
 */
@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    
    List<Resource> findByType(DonationType type);
    
    List<Resource> findByReliefCenter(ReliefCenter reliefCenter);
    
    List<Resource> findByQuantityLessThan(Integer quantity);
    
    List<Resource> findByNameContainingIgnoreCase(String name);
}

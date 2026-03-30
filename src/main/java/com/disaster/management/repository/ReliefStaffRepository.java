package com.disaster.management.repository;

import com.disaster.management.model.entity.ReliefStaff;
import com.disaster.management.model.entity.ReliefCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * REPOSITORY INTERFACE - ReliefStaff Repository
 * Data Access Layer for ReliefStaff entity
 */
@Repository
public interface ReliefStaffRepository extends JpaRepository<ReliefStaff, Integer> {
    
    List<ReliefStaff> findByReliefCenter(ReliefCenter reliefCenter);
    
    List<ReliefStaff> findByDesignation(String designation);
}

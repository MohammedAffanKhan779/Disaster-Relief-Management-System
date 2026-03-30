package com.disaster.management.repository;

import com.disaster.management.model.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * REPOSITORY INTERFACE - Donor Repository
 * Data Access Layer for Donor entity
 */
@Repository
public interface DonorRepository extends JpaRepository<Donor, Integer> {
    
    List<Donor> findByDonorType(String donorType);
}

package com.disaster.management.repository;

import com.disaster.management.model.entity.Donation;
import com.disaster.management.model.entity.Donor;
import com.disaster.management.model.entity.DisasterEvent;
import com.disaster.management.model.enums.DonationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * REPOSITORY INTERFACE - Donation Repository
 * Data Access Layer for Donation entity
 */
@Repository
public interface DonationRepository extends JpaRepository<Donation, Integer> {
    
    List<Donation> findByDonor(Donor donor);
    
    List<Donation> findByDisasterEvent(DisasterEvent disasterEvent);
    
    List<Donation> findByType(DonationType type);
    
    List<Donation> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT SUM(d.amount) FROM Donation d WHERE d.type = :type")
    Double getTotalAmountByType(DonationType type);
    
    @Query("SELECT SUM(d.amount) FROM Donation d WHERE d.disasterEvent = :event")
    Double getTotalAmountByEvent(DisasterEvent event);
}

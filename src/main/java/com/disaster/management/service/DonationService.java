package com.disaster.management.service;

import com.disaster.management.model.entity.Donation;
import com.disaster.management.model.entity.DisasterEvent;
import com.disaster.management.model.entity.Donor;
import com.disaster.management.model.enums.DonationType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * SERVICE INTERFACE - Donation Service
 * Defines business logic operations for Donation management
 */
public interface DonationService {
    
    Donation saveDonation(Donation donation);
    
    Optional<Donation> getDonationById(Integer id);
    
    List<Donation> getAllDonations();
    
    Donation updateDonation(Donation donation);
    
    void deleteDonation(Integer id);
    
    List<Donation> getDonationsByDonor(Donor donor);
    
    List<Donation> getDonationsByEvent(DisasterEvent disasterEvent);
    
    List<Donation> getDonationsByType(DonationType type);
    
    List<Donation> getDonationsByDateRange(LocalDate startDate, LocalDate endDate);
    
    Double getTotalDonationsByType(DonationType type);
    
    Double getTotalDonationsByEvent(DisasterEvent event);
}

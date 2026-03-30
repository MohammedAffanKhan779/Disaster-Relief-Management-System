package com.disaster.management.service.impl;

import com.disaster.management.model.entity.Donation;
import com.disaster.management.model.entity.DisasterEvent;
import com.disaster.management.model.entity.Donor;
import com.disaster.management.model.enums.DonationType;
import com.disaster.management.repository.DonationRepository;
import com.disaster.management.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * SERVICE IMPLEMENTATION - Donation Service
 * Implements business logic for Donation management
 */
@Service
@Transactional
public class DonationServiceImpl implements DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Override
    public Donation saveDonation(Donation donation) {
        donation.processDonation();
        return donationRepository.save(donation);
    }

    @Override
    public Optional<Donation> getDonationById(Integer id) {
        return donationRepository.findById(id);
    }

    @Override
    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    @Override
    public Donation updateDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    @Override
    public void deleteDonation(Integer id) {
        donationRepository.deleteById(id);
    }

    @Override
    public List<Donation> getDonationsByDonor(Donor donor) {
        return donationRepository.findByDonor(donor);
    }

    @Override
    public List<Donation> getDonationsByEvent(DisasterEvent disasterEvent) {
        return donationRepository.findByDisasterEvent(disasterEvent);
    }

    @Override
    public List<Donation> getDonationsByType(DonationType type) {
        return donationRepository.findByType(type);
    }

    @Override
    public List<Donation> getDonationsByDateRange(LocalDate startDate, LocalDate endDate) {
        return donationRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public Double getTotalDonationsByType(DonationType type) {
        Double total = donationRepository.getTotalAmountByType(type);
        return total != null ? total : 0.0;
    }

    @Override
    public Double getTotalDonationsByEvent(DisasterEvent event) {
        Double total = donationRepository.getTotalAmountByEvent(event);
        return total != null ? total : 0.0;
    }
}

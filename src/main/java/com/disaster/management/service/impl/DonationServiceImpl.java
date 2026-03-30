package com.disaster.management.service.impl;

import com.disaster.management.model.entity.Donation;
import com.disaster.management.model.entity.DisasterEvent;
import com.disaster.management.model.entity.Donor;
import com.disaster.management.model.entity.Resource;
import com.disaster.management.model.enums.DonationType;
import com.disaster.management.repository.DonationRepository;
import com.disaster.management.service.DonationService;
import com.disaster.management.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * SERVICE IMPLEMENTATION - Donation Service
 * Implements business logic for Donation management with automatic resource updates
 */
@Service
@Transactional
public class DonationServiceImpl implements DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private ResourceService resourceService;

    @Override
    public Donation saveDonation(Donation donation) {
        // Process the donation (generates receipt)
        donation.processDonation();
        Donation savedDonation = donationRepository.save(donation);

        // If donation is material (not money), update resource inventory
        if (donation.getType() != null && donation.getType() != DonationType.MONEY) {
            updateResourceFromDonation(savedDonation);
        }

        return savedDonation;
    }

    /**
     * Updates or creates a resource based on the donation.
     * For material donations (FOOD, MEDICINE, CLOTHES, WATER, EQUIPMENT),
     * this will find or create the corresponding resource and increase its quantity.
     */
    private void updateResourceFromDonation(Donation donation) {
        DonationType type = donation.getType();
        
        // Determine resource name based on donation type
        String resourceName = type.name() + " Supplies";
        if (donation.getDescription() != null && !donation.getDescription().isEmpty()) {
            // Use description as more specific name if available
            resourceName = donation.getDescription();
            if (resourceName.length() > 50) {
                resourceName = resourceName.substring(0, 50);
            }
        }

        // Find or create the resource
        Resource resource = resourceService.findOrCreateResource(resourceName, type, null);

        // Calculate quantity from donation amount (for material donations, amount represents units)
        int quantityToAdd = 1; // Default to 1 unit
        if (donation.getAmount() != null && donation.getAmount() > 0) {
            quantityToAdd = donation.getAmount().intValue();
        }

        // Increase resource quantity
        resourceService.increaseQuantity(resource.getResourceId(), quantityToAdd);
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

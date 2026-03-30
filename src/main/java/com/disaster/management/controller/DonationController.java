package com.disaster.management.controller;

import com.disaster.management.model.entity.Donation;
import com.disaster.management.model.enums.DonationType;
import com.disaster.management.model.enums.PaymentMethod;
import com.disaster.management.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTROLLER CLASS - Donation Controller
 * Handles HTTP requests for Donation operations
 */
@Controller
@RequestMapping("/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;

    // Display all donations
    @GetMapping
    public String getAllDonations(Model model) {
        List<Donation> donations = donationService.getAllDonations();
        model.addAttribute("donations", donations);
        return "donations/list";
    }

    // Show donation form
    @GetMapping("/new")
    public String showDonationForm(Model model) {
        model.addAttribute("donation", new Donation());
        model.addAttribute("types", DonationType.values());
        model.addAttribute("paymentMethods", PaymentMethod.values());
        return "donations/form";
    }

    // Process donation
    @PostMapping
    public String createDonation(@ModelAttribute Donation donation) {
        donationService.saveDonation(donation);
        return "redirect:/donations?success";
    }

    // REST API Endpoints
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Donation>> getDonationsAPI() {
        return ResponseEntity.ok(donationService.getAllDonations());
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Donation> getDonationByIdAPI(@PathVariable Integer id) {
        return donationService.getDonationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<Donation> createDonationAPI(@RequestBody Donation donation) {
        Donation saved = donationService.saveDonation(donation);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}

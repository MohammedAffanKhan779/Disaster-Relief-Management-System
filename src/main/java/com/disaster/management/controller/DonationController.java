package com.disaster.management.controller;

import com.disaster.management.model.entity.Donation;
import com.disaster.management.model.entity.User;
import com.disaster.management.model.enums.DonationType;
import com.disaster.management.model.enums.PaymentMethod;
import com.disaster.management.model.enums.UserRole;
import com.disaster.management.service.DonationService;
import jakarta.servlet.http.HttpSession;
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

    // Display all donations (all logged-in users)
    @GetMapping
    public String getAllDonations(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        List<Donation> donations = donationService.getAllDonations();
        model.addAttribute("donations", donations);
        model.addAttribute("loggedInUser", loggedInUser);
        return "donations/list";
    }

    // Show donation form (DONOR only)
    @GetMapping("/new")
    public String showDonationForm(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        // Only DONOR and ADMIN can create donations
        if (loggedInUser.getRole() != UserRole.DONOR && loggedInUser.getRole() != UserRole.ADMIN) {
            return "redirect:/dashboard?accessDenied";
        }
        model.addAttribute("donation", new Donation());
        model.addAttribute("types", DonationType.values());
        model.addAttribute("paymentMethods", PaymentMethod.values());
        model.addAttribute("loggedInUser", loggedInUser);
        return "donations/form";
    }

    // Process donation (DONOR only)
    @PostMapping
    public String createDonation(@ModelAttribute Donation donation, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (loggedInUser.getRole() != UserRole.DONOR && loggedInUser.getRole() != UserRole.ADMIN) {
            return "redirect:/dashboard?accessDenied";
        }
        donationService.saveDonation(donation);
        return "redirect:/donations?success";
    }

    // View donation details (all logged-in users)
    @GetMapping("/{id}")
    public String viewDonation(@PathVariable Integer id, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        donationService.getDonationById(id).ifPresent(donation -> {
            model.addAttribute("donation", donation);
        });
        model.addAttribute("loggedInUser", loggedInUser);
        return "donations/view";
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

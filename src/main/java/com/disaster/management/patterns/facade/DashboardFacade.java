package com.disaster.management.patterns.facade;

import com.disaster.management.model.entity.User;
import com.disaster.management.service.DisasterEventService;
import com.disaster.management.service.DonationService;
import com.disaster.management.service.ReliefCenterService;
import com.disaster.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * Facade pattern that hides dashboard data aggregation behind role-specific methods.
 */
@Service
public class DashboardFacade {

    private final DisasterEventService disasterEventService;
    private final DonationService donationService;
    private final ReliefCenterService reliefCenterService;
    private final UserService userService;

    @Autowired
    public DashboardFacade(DisasterEventService disasterEventService,
                           DonationService donationService,
                           ReliefCenterService reliefCenterService,
                           UserService userService) {
        this.disasterEventService = disasterEventService;
        this.donationService = donationService;
        this.reliefCenterService = reliefCenterService;
        this.userService = userService;
    }

    public void populateAdminDashboard(Model model, User loggedInUser) {
        applyBaseAttributes(model, loggedInUser, "admin");
        model.addAttribute("disasterCount", disasterEventService.getAllDisasterEvents().size());
        model.addAttribute("donationCount", donationService.getAllDonations().size());
        model.addAttribute("centerCount", reliefCenterService.getAllReliefCenters().size());
        model.addAttribute("userCount", userService.getAllUsers().size());
    }

    public void populateDonorDashboard(Model model, User loggedInUser) {
        applyBaseAttributes(model, loggedInUser, "donor");
        model.addAttribute("donationCount", donationService.getAllDonations().size());
        model.addAttribute("disasterCount", disasterEventService.getAllDisasterEvents().size());
    }

    public void populateVolunteerDashboard(Model model, User loggedInUser) {
        applyBaseAttributes(model, loggedInUser, "volunteer");
        model.addAttribute("disasterCount", disasterEventService.getAllDisasterEvents().size());
        model.addAttribute("centerCount", reliefCenterService.getAllReliefCenters().size());
    }

    public void populateStaffDashboard(Model model, User loggedInUser) {
        applyBaseAttributes(model, loggedInUser, "staff");
        model.addAttribute("disasterCount", disasterEventService.getAllDisasterEvents().size());
        model.addAttribute("centerCount", reliefCenterService.getAllReliefCenters().size());
    }

    public void populateAuthorityDashboard(Model model, User loggedInUser) {
        applyBaseAttributes(model, loggedInUser, "authority");
        model.addAttribute("disasterCount", disasterEventService.getAllDisasterEvents().size());
        model.addAttribute("donationCount", donationService.getAllDonations().size());
        model.addAttribute("centerCount", reliefCenterService.getAllReliefCenters().size());
    }

    private void applyBaseAttributes(Model model, User loggedInUser, String dashboardType) {
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("dashboardType", dashboardType);
    }
}

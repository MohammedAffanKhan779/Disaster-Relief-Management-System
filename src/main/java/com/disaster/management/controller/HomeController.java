package com.disaster.management.controller;

import com.disaster.management.model.entity.User;
import com.disaster.management.model.enums.UserRole;
import com.disaster.management.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * CONTROLLER CLASS - Home Controller
 * Handles requests for the main pages
 */
@Controller
public class HomeController {

    @Autowired
    private DisasterEventService disasterEventService;
    
    @Autowired
    private DonationService donationService;
    
    @Autowired
    private ReliefCenterService reliefCenterService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(HttpSession session) {
        // If not logged in, redirect to login
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/users/login";
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        
        // Redirect to role-specific dashboard
        return switch (loggedInUser.getRole()) {
            case ADMIN -> "redirect:/dashboard/admin";
            case DONOR -> "redirect:/dashboard/donor";
            case VOLUNTEER -> "redirect:/dashboard/volunteer";
            case RELIEF_STAFF -> "redirect:/dashboard/staff";
            case AUTHORITY -> "redirect:/dashboard/authority";
        };
    }

    // ==================== ROLE-BASED DASHBOARDS ====================

    @GetMapping("/dashboard/admin")
    public String adminDashboard(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (loggedInUser.getRole() != UserRole.ADMIN) {
            return "redirect:/dashboard?accessDenied";
        }
        
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("dashboardType", "admin");
        model.addAttribute("disasterCount", disasterEventService.getAllDisasterEvents().size());
        model.addAttribute("donationCount", donationService.getAllDonations().size());
        model.addAttribute("centerCount", reliefCenterService.getAllReliefCenters().size());
        model.addAttribute("userCount", userService.getAllUsers().size());
        return "dashboard";
    }

    @GetMapping("/dashboard/donor")
    public String donorDashboard(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (loggedInUser.getRole() != UserRole.DONOR) {
            return "redirect:/dashboard?accessDenied";
        }
        
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("dashboardType", "donor");
        model.addAttribute("donationCount", donationService.getAllDonations().size());
        model.addAttribute("disasterCount", disasterEventService.getAllDisasterEvents().size());
        return "dashboard";
    }

    @GetMapping("/dashboard/volunteer")
    public String volunteerDashboard(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (loggedInUser.getRole() != UserRole.VOLUNTEER) {
            return "redirect:/dashboard?accessDenied";
        }
        
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("dashboardType", "volunteer");
        model.addAttribute("disasterCount", disasterEventService.getAllDisasterEvents().size());
        model.addAttribute("centerCount", reliefCenterService.getAllReliefCenters().size());
        return "dashboard";
    }

    @GetMapping("/dashboard/staff")
    public String staffDashboard(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (loggedInUser.getRole() != UserRole.RELIEF_STAFF) {
            return "redirect:/dashboard?accessDenied";
        }
        
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("dashboardType", "staff");
        model.addAttribute("disasterCount", disasterEventService.getAllDisasterEvents().size());
        model.addAttribute("centerCount", reliefCenterService.getAllReliefCenters().size());
        return "dashboard";
    }

    @GetMapping("/dashboard/authority")
    public String authorityDashboard(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (loggedInUser.getRole() != UserRole.AUTHORITY) {
            return "redirect:/dashboard?accessDenied";
        }
        
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("dashboardType", "authority");
        model.addAttribute("disasterCount", disasterEventService.getAllDisasterEvents().size());
        model.addAttribute("donationCount", donationService.getAllDonations().size());
        model.addAttribute("centerCount", reliefCenterService.getAllReliefCenters().size());
        return "dashboard";
    }

    @GetMapping("/about")
    public String about(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);
        return "about";
    }
}

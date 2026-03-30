package com.disaster.management.controller;

import com.disaster.management.model.entity.ReliefCenter;
import com.disaster.management.model.entity.User;
import com.disaster.management.model.enums.UserRole;
import com.disaster.management.service.ReliefCenterService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTROLLER CLASS - ReliefCenter Controller
 * Handles HTTP requests for Relief Center operations
 */
@Controller
@RequestMapping("/relief-centers")
public class ReliefCenterController {

    @Autowired
    private ReliefCenterService reliefCenterService;

    // Helper method to check login
    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    // Helper method to check if user can manage centers (ADMIN, RELIEF_STAFF)
    private boolean canManageCenters(User user) {
        return user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.RELIEF_STAFF;
    }

    // Display all relief centers (all logged-in users)
    @GetMapping
    public String getAllReliefCenters(Model model, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        
        List<ReliefCenter> reliefCenters = reliefCenterService.getAllReliefCenters();
        model.addAttribute("reliefCenters", reliefCenters);
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("canManage", canManageCenters(loggedInUser));
        return "relief-centers/list";
    }

    // Show form to create new relief center (ADMIN, RELIEF_STAFF)
    @GetMapping("/new")
    public String showCreateForm(Model model, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canManageCenters(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        model.addAttribute("reliefCenter", new ReliefCenter());
        model.addAttribute("loggedInUser", loggedInUser);
        return "relief-centers/form";
    }

    // Create new relief center (ADMIN, RELIEF_STAFF)
    @PostMapping
    public String createReliefCenter(@ModelAttribute ReliefCenter reliefCenter, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canManageCenters(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        reliefCenterService.saveReliefCenter(reliefCenter);
        return "redirect:/relief-centers?success";
    }

    // View relief center details (all logged-in users)
    @GetMapping("/{id}")
    public String viewReliefCenter(@PathVariable Integer id, Model model, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        
        reliefCenterService.getReliefCenterById(id).ifPresent(reliefCenter -> {
            model.addAttribute("reliefCenter", reliefCenter);
        });
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("canManage", canManageCenters(loggedInUser));
        return "relief-centers/view";
    }

    // Show form to edit relief center (ADMIN, RELIEF_STAFF)
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canManageCenters(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        reliefCenterService.getReliefCenterById(id).ifPresent(reliefCenter -> {
            model.addAttribute("reliefCenter", reliefCenter);
        });
        model.addAttribute("loggedInUser", loggedInUser);
        return "relief-centers/form";
    }

    // Update relief center (ADMIN, RELIEF_STAFF)
    @PostMapping("/{id}")
    public String updateReliefCenter(@PathVariable Integer id, @ModelAttribute ReliefCenter reliefCenter, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canManageCenters(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        reliefCenter.setCenterId(id);
        reliefCenterService.updateReliefCenter(reliefCenter);
        return "redirect:/relief-centers/" + id + "?updated";
    }

    // Delete relief center (ADMIN only)
    @PostMapping("/{id}/delete")
    public String deleteReliefCenter(@PathVariable Integer id, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (loggedInUser.getRole() != UserRole.ADMIN) {
            return "redirect:/dashboard?accessDenied";
        }
        
        reliefCenterService.deleteReliefCenter(id);
        return "redirect:/relief-centers?deleted";
    }

    // REST API Endpoints
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<ReliefCenter>> getReliefCentersAPI() {
        return ResponseEntity.ok(reliefCenterService.getAllReliefCenters());
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<ReliefCenter> getReliefCenterByIdAPI(@PathVariable Integer id) {
        return reliefCenterService.getReliefCenterById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<ReliefCenter> createReliefCenterAPI(@RequestBody ReliefCenter reliefCenter) {
        ReliefCenter saved = reliefCenterService.saveReliefCenter(reliefCenter);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<ReliefCenter> updateReliefCenterAPI(@PathVariable Integer id, @RequestBody ReliefCenter reliefCenter) {
        return reliefCenterService.getReliefCenterById(id)
                .map(existing -> {
                    reliefCenter.setCenterId(id);
                    return ResponseEntity.ok(reliefCenterService.updateReliefCenter(reliefCenter));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteReliefCenterAPI(@PathVariable Integer id) {
        reliefCenterService.deleteReliefCenter(id);
        return ResponseEntity.noContent().build();
    }
}

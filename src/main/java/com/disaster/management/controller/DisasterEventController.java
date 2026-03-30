package com.disaster.management.controller;

import com.disaster.management.model.entity.DisasterEvent;
import com.disaster.management.model.entity.User;
import com.disaster.management.model.enums.DisasterStatus;
import com.disaster.management.model.enums.DisasterType;
import com.disaster.management.model.enums.SeverityLevel;
import com.disaster.management.model.enums.UserRole;
import com.disaster.management.service.DisasterEventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTROLLER CLASS - DisasterEvent Controller
 * Handles HTTP requests for Disaster Event operations
 */
@Controller
@RequestMapping("/disasters")
public class DisasterEventController {

    @Autowired
    private DisasterEventService disasterEventService;

    // Helper method to check login
    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    // Helper method to check if user can manage disasters (ADMIN, AUTHORITY)
    private boolean canManageDisasters(User user) {
        return user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.AUTHORITY;
    }

    // Display all disaster events (all logged-in users can view)
    @GetMapping
    public String getAllDisasters(Model model, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        
        List<DisasterEvent> disasters = disasterEventService.getAllDisasterEvents();
        model.addAttribute("disasters", disasters);
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("canManage", canManageDisasters(loggedInUser));
        return "disasters/list";
    }

    // Show form to create new disaster event (ADMIN, AUTHORITY)
    @GetMapping("/new")
    public String showCreateForm(Model model, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canManageDisasters(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        model.addAttribute("disaster", new DisasterEvent());
        model.addAttribute("types", DisasterType.values());
        model.addAttribute("severities", SeverityLevel.values());
        model.addAttribute("statuses", DisasterStatus.values());
        model.addAttribute("loggedInUser", loggedInUser);
        return "disasters/form";
    }

    // Create new disaster event (ADMIN, AUTHORITY)
    @PostMapping
    public String createDisaster(@ModelAttribute DisasterEvent disaster, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canManageDisasters(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        disasterEventService.saveDisasterEvent(disaster);
        return "redirect:/disasters?success";
    }

    // View disaster details (all logged-in users can view)
    @GetMapping("/{id}")
    public String viewDisaster(@PathVariable Integer id, Model model, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        
        disasterEventService.getDisasterEventById(id).ifPresent(disaster -> {
            model.addAttribute("disaster", disaster);
        });
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("canManage", canManageDisasters(loggedInUser));
        return "disasters/view";
    }

    // Show form to edit disaster event (ADMIN, AUTHORITY)
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canManageDisasters(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        disasterEventService.getDisasterEventById(id).ifPresent(disaster -> {
            model.addAttribute("disaster", disaster);
            model.addAttribute("types", DisasterType.values());
            model.addAttribute("severities", SeverityLevel.values());
            model.addAttribute("statuses", DisasterStatus.values());
        });
        model.addAttribute("loggedInUser", loggedInUser);
        return "disasters/form";
    }

    // Update disaster event (ADMIN, AUTHORITY)
    @PostMapping("/{id}")
    public String updateDisaster(@PathVariable Integer id, @ModelAttribute DisasterEvent disaster, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canManageDisasters(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        disaster.setEventId(id);
        disasterEventService.updateDisasterEvent(disaster);
        return "redirect:/disasters/" + id + "?updated";
    }

    // Delete disaster event (ADMIN only)
    @PostMapping("/{id}/delete")
    public String deleteDisaster(@PathVariable Integer id, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (loggedInUser.getRole() != UserRole.ADMIN) {
            return "redirect:/dashboard?accessDenied";
        }
        
        disasterEventService.deleteDisasterEvent(id);
        return "redirect:/disasters?deleted";
    }

    // REST API Endpoints
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<DisasterEvent>> getDisastersAPI() {
        return ResponseEntity.ok(disasterEventService.getAllDisasterEvents());
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<DisasterEvent> getDisasterByIdAPI(@PathVariable Integer id) {
        return disasterEventService.getDisasterEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<DisasterEvent> createDisasterAPI(@RequestBody DisasterEvent disaster) {
        DisasterEvent saved = disasterEventService.saveDisasterEvent(disaster);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<DisasterEvent> updateDisasterAPI(@PathVariable Integer id, @RequestBody DisasterEvent disaster) {
        return disasterEventService.getDisasterEventById(id)
                .map(existing -> {
                    disaster.setEventId(id);
                    return ResponseEntity.ok(disasterEventService.updateDisasterEvent(disaster));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteDisasterAPI(@PathVariable Integer id) {
        disasterEventService.deleteDisasterEvent(id);
        return ResponseEntity.noContent().build();
    }
}

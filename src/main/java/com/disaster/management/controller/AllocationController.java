package com.disaster.management.controller;

import com.disaster.management.model.entity.Allocation;
import com.disaster.management.model.entity.ReliefCenter;
import com.disaster.management.model.entity.Resource;
import com.disaster.management.model.entity.User;
import com.disaster.management.model.enums.AllocationStatus;
import com.disaster.management.model.enums.UserRole;
import com.disaster.management.service.AllocationService;
import com.disaster.management.service.ReliefCenterService;
import com.disaster.management.service.ResourceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for managing resource allocations.
 * Handles allocation creation, status updates, and tracking.
 */
@Controller
@RequestMapping("/allocations")
public class AllocationController {

    private final AllocationService allocationService;
    private final ResourceService resourceService;
    private final ReliefCenterService reliefCenterService;

    @Autowired
    public AllocationController(AllocationService allocationService,
                                 ResourceService resourceService,
                                 ReliefCenterService reliefCenterService) {
        this.allocationService = allocationService;
        this.resourceService = resourceService;
        this.reliefCenterService = reliefCenterService;
    }

    // Helper method to check login
    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    // Helper method to check if user can manage allocations (ADMIN, RELIEF_STAFF)
    private boolean canManageAllocations(User user) {
        return user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.RELIEF_STAFF;
    }

    // ==================== UI ENDPOINTS ====================

    /**
     * List all allocations (all logged-in users)
     */
    @GetMapping
    public String listAllocations(@RequestParam(required = false) String status, Model model, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        
        List<Allocation> allocations;
        if (status != null && !status.isEmpty()) {
            try {
                AllocationStatus statusEnum = AllocationStatus.valueOf(status.toUpperCase());
                allocations = allocationService.getAllocationsByStatus(statusEnum);
            } catch (IllegalArgumentException e) {
                allocations = allocationService.getAllAllocations();
            }
        } else {
            allocations = allocationService.getAllAllocations();
        }
        model.addAttribute("allocations", allocations);
        model.addAttribute("statuses", AllocationStatus.values());
        model.addAttribute("currentStatus", status);
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("canManage", canManageAllocations(loggedInUser));
        return "allocations/list";
    }

    /**
     * Show form to create new allocation (ADMIN, RELIEF_STAFF)
     */
    @GetMapping("/new")
    public String showCreateForm(Model model, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canManageAllocations(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        model.addAttribute("allocation", new Allocation());
        model.addAttribute("resources", resourceService.getAllResources());
        model.addAttribute("reliefCenters", reliefCenterService.getAllReliefCenters());
        model.addAttribute("statuses", AllocationStatus.values());
        model.addAttribute("loggedInUser", loggedInUser);
        return "allocations/form";
    }

    /**
     * Create a new allocation (ADMIN, RELIEF_STAFF)
     */
    @PostMapping
    public String createAllocation(@ModelAttribute Allocation allocation,
                                    @RequestParam Integer resourceId,
                                    @RequestParam Integer centerId,
                                    HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canManageAllocations(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        Resource resource = resourceService.getResourceById(resourceId)
                .orElseThrow(() -> new IllegalArgumentException("Resource not found"));
        ReliefCenter center = reliefCenterService.getReliefCenterById(centerId)
                .orElseThrow(() -> new IllegalArgumentException("Relief center not found"));
        
        allocation.setResource(resource);
        allocation.setReliefCenter(center);
        allocation.setAllocationDate(LocalDate.now());
        if (allocation.getStatus() == null) {
            allocation.setStatus(AllocationStatus.SCHEDULED);
        }
        allocationService.createAllocation(allocation);
        
        return "redirect:/allocations?success";
    }

    /**
     * View a specific allocation (all logged-in users)
     */
    @GetMapping("/{id}")
    public String viewAllocation(@PathVariable Integer id, Model model, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        
        Allocation allocation = allocationService.getAllocationById(id)
                .orElse(null);
        model.addAttribute("allocation", allocation);
        model.addAttribute("statuses", AllocationStatus.values());
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("canManage", canManageAllocations(loggedInUser));
        return "allocations/view";
    }

    /**
     * Update allocation status (ADMIN, RELIEF_STAFF)
     */
    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Integer id, 
                                @RequestParam String status,
                                HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canManageAllocations(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        try {
            AllocationStatus newStatus = AllocationStatus.valueOf(status.toUpperCase());
            allocationService.updateAllocationStatus(id, newStatus);
            return "redirect:/allocations/" + id + "?updated";
        } catch (IllegalStateException e) {
            return "redirect:/allocations/" + id + "?error=" + e.getMessage();
        }
    }

    /**
     * Mark allocation as in transit (ADMIN, RELIEF_STAFF)
     */
    @PostMapping("/{id}/dispatch")
    public String dispatchAllocation(@PathVariable Integer id, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canManageAllocations(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        allocationService.updateAllocationStatus(id, AllocationStatus.IN_TRANSIT);
        return "redirect:/allocations/" + id + "?dispatched";
    }

    /**
     * Mark allocation as delivered (ADMIN, RELIEF_STAFF)
     */
    @PostMapping("/{id}/deliver")
    public String deliverAllocation(@PathVariable Integer id, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canManageAllocations(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        allocationService.updateAllocationStatus(id, AllocationStatus.DELIVERED);
        return "redirect:/allocations/" + id + "?delivered";
    }

    /**
     * Cancel an allocation (ADMIN only)
     */
    @PostMapping("/{id}/cancel")
    public String cancelAllocation(@PathVariable Integer id, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (loggedInUser.getRole() != UserRole.ADMIN) {
            return "redirect:/dashboard?accessDenied";
        }
        
        allocationService.cancelAllocation(id);
        return "redirect:/allocations/" + id + "?cancelled";
    }

    /**
     * Delete a cancelled allocation (ADMIN only)
     */
    @PostMapping("/{id}/delete")
    public String deleteAllocation(@PathVariable Integer id, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (loggedInUser.getRole() != UserRole.ADMIN) {
            return "redirect:/dashboard?accessDenied";
        }
        
        allocationService.deleteAllocation(id);
        return "redirect:/allocations?deleted";
    }

    // ==================== REST API ENDPOINTS ====================

    /**
     * Get all allocations (API)
     */
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Allocation>> getAllAllocationsApi() {
        return ResponseEntity.ok(allocationService.getAllAllocations());
    }

    /**
     * Get allocation by ID (API)
     */
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Allocation> getAllocationApi(@PathVariable Integer id) {
        return allocationService.getAllocationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create allocation (API)
     */
    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<Allocation> createAllocationApi(@RequestBody Allocation allocation) {
        Allocation saved = allocationService.createAllocation(allocation);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Update status (API)
     */
    @PutMapping("/api/{id}/status")
    @ResponseBody
    public ResponseEntity<Allocation> updateStatusApi(@PathVariable Integer id,
                                                       @RequestParam String status) {
        try {
            AllocationStatus newStatus = AllocationStatus.valueOf(status.toUpperCase());
            Allocation updated = allocationService.updateAllocationStatus(id, newStatus);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get in-transit count (API)
     */
    @GetMapping("/api/in-transit-count")
    @ResponseBody
    public ResponseEntity<Long> getInTransitCount() {
        return ResponseEntity.ok(allocationService.getInTransitCount());
    }
}

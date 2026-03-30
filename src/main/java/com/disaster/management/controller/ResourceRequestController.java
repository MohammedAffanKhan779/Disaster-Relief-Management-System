package com.disaster.management.controller;

import com.disaster.management.model.entity.Administrator;
import com.disaster.management.model.entity.ReliefCenter;
import com.disaster.management.model.entity.Resource;
import com.disaster.management.model.entity.ResourceRequest;
import com.disaster.management.model.entity.User;
import com.disaster.management.model.enums.RequestStatus;
import com.disaster.management.model.enums.UserRole;
import com.disaster.management.service.ReliefCenterService;
import com.disaster.management.service.ResourceRequestService;
import com.disaster.management.service.ResourceService;
import com.disaster.management.service.impl.ResourceRequestServiceImpl;
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
 * Controller for managing resource requests.
 * Handles request submission, approval, rejection, and tracking.
 */
@Controller
@RequestMapping("/resource-requests")
public class ResourceRequestController {

    private final ResourceRequestService requestService;
    private final ResourceService resourceService;
    private final ReliefCenterService reliefCenterService;

    @Autowired
    public ResourceRequestController(ResourceRequestService requestService,
                                       ResourceService resourceService,
                                       ReliefCenterService reliefCenterService) {
        this.requestService = requestService;
        this.resourceService = resourceService;
        this.reliefCenterService = reliefCenterService;
    }

    // Helper method to check login
    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    // Helper method to check if user can submit requests (RELIEF_STAFF, VOLUNTEER, ADMIN)
    private boolean canSubmitRequests(User user) {
        return user.getRole() == UserRole.RELIEF_STAFF || 
               user.getRole() == UserRole.VOLUNTEER || 
               user.getRole() == UserRole.ADMIN;
    }

    // Helper method to check if user can approve/reject (ADMIN only)
    private boolean canApproveRequests(User user) {
        return user.getRole() == UserRole.ADMIN;
    }

    // ==================== UI ENDPOINTS ====================

    /**
     * List all resource requests (all logged-in users)
     */
    @GetMapping
    public String listRequests(@RequestParam(required = false) String status, Model model, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        
        List<ResourceRequest> requests;
        if (status != null && !status.isEmpty()) {
            try {
                RequestStatus statusEnum = RequestStatus.valueOf(status.toUpperCase());
                requests = requestService.getRequestsByStatus(statusEnum);
            } catch (IllegalArgumentException e) {
                requests = requestService.getAllRequests();
            }
        } else {
            requests = requestService.getAllRequests();
        }
        model.addAttribute("requests", requests);
        model.addAttribute("statuses", RequestStatus.values());
        model.addAttribute("currentStatus", status);
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("canApprove", canApproveRequests(loggedInUser));
        return "resource-requests/list";
    }

    /**
     * Show form to create new request (RELIEF_STAFF, VOLUNTEER, ADMIN)
     */
    @GetMapping("/new")
    public String showCreateForm(Model model, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canSubmitRequests(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        model.addAttribute("resourceRequest", new ResourceRequest());
        model.addAttribute("resources", resourceService.getAllResources());
        model.addAttribute("reliefCenters", reliefCenterService.getAllReliefCenters());
        model.addAttribute("loggedInUser", loggedInUser);
        return "resource-requests/form";
    }

    /**
     * Submit a new resource request (RELIEF_STAFF, VOLUNTEER, ADMIN)
     */
    @PostMapping
    public String createRequest(@ModelAttribute ResourceRequest request,
                                 @RequestParam Integer resourceId,
                                 @RequestParam Integer centerId,
                                 HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canSubmitRequests(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        Resource resource = resourceService.getResourceById(resourceId)
                .orElseThrow(() -> new IllegalArgumentException("Resource not found"));
        ReliefCenter center = reliefCenterService.getReliefCenterById(centerId)
                .orElseThrow(() -> new IllegalArgumentException("Relief center not found"));
        
        request.setResource(resource);
        request.setReliefCenter(center);
        request.setRequestDate(LocalDate.now());
        requestService.submitRequest(request);
        
        return "redirect:/resource-requests?success";
    }

    /**
     * View a specific request (all logged-in users)
     */
    @GetMapping("/{id}")
    public String viewRequest(@PathVariable Integer id, Model model, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        
        ResourceRequest request = requestService.getRequestById(id)
                .orElse(null);
        model.addAttribute("resourceRequest", request);
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("canApprove", canApproveRequests(loggedInUser));
        return "resource-requests/view";
    }

    /**
     * Approve a request (ADMIN only)
     */
    @PostMapping("/{id}/approve")
    public String approveRequest(@PathVariable Integer id, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canApproveRequests(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        Administrator approver = (loggedInUser instanceof Administrator) ? (Administrator) loggedInUser : null;
        ((ResourceRequestServiceImpl) requestService).approveRequest(id, approver);
        return "redirect:/resource-requests/" + id + "?approved";
    }

    /**
     * Reject a request (ADMIN only)
     */
    @PostMapping("/{id}/reject")
    public String rejectRequest(@PathVariable Integer id, 
                                 @RequestParam(required = false) String reason,
                                 HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canApproveRequests(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        requestService.rejectRequest(id, reason);
        return "redirect:/resource-requests/" + id + "?rejected";
    }

    /**
     * Delete a pending request (owner or ADMIN)
     */
    @PostMapping("/{id}/delete")
    public String deleteRequest(@PathVariable Integer id, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        // Only admin can delete any request
        if (loggedInUser.getRole() != UserRole.ADMIN) {
            return "redirect:/dashboard?accessDenied";
        }
        
        requestService.deleteRequest(id);
        return "redirect:/resource-requests?deleted";
    }

    // ==================== REST API ENDPOINTS ====================

    /**
     * Get all requests (API)
     */
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<ResourceRequest>> getAllRequestsApi() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    /**
     * Get request by ID (API)
     */
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<ResourceRequest> getRequestApi(@PathVariable Integer id) {
        return requestService.getRequestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Submit request (API)
     */
    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<ResourceRequest> submitRequestApi(@RequestBody ResourceRequest request) {
        ResourceRequest saved = requestService.submitRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Approve request (API)
     */
    @PostMapping("/api/{id}/approve")
    @ResponseBody
    public ResponseEntity<ResourceRequest> approveRequestApi(@PathVariable Integer id) {
        try {
            ResourceRequest approved = requestService.approveRequest(id);
            return ResponseEntity.ok(approved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Reject request (API)
     */
    @PostMapping("/api/{id}/reject")
    @ResponseBody
    public ResponseEntity<ResourceRequest> rejectRequestApi(@PathVariable Integer id,
                                                             @RequestParam(required = false) String reason) {
        try {
            ResourceRequest rejected = requestService.rejectRequest(id, reason);
            return ResponseEntity.ok(rejected);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get pending count (API)
     */
    @GetMapping("/api/pending-count")
    @ResponseBody
    public ResponseEntity<Long> getPendingCount() {
        return ResponseEntity.ok(requestService.getPendingRequestCount());
    }
}

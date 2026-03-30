package com.disaster.management.controller;

import com.disaster.management.model.entity.DisasterEvent;
import com.disaster.management.model.entity.Report;
import com.disaster.management.model.entity.User;
import com.disaster.management.model.enums.UserRole;
import com.disaster.management.service.DisasterEventService;
import com.disaster.management.service.ReportService;
import com.disaster.management.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for managing reports.
 * Handles report generation, viewing, and export.
 */
@Controller
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;
    private final DisasterEventService disasterEventService;
    private final UserService userService;

    @Autowired
    public ReportController(ReportService reportService,
                             DisasterEventService disasterEventService,
                             UserService userService) {
        this.reportService = reportService;
        this.disasterEventService = disasterEventService;
        this.userService = userService;
    }

    // Helper method to check login
    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    // Helper method to check if user can generate reports (ADMIN, AUTHORITY)
    private boolean canGenerateReports(User user) {
        return user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.AUTHORITY;
    }

    // ==================== UI ENDPOINTS ====================

    /**
     * List all reports (ADMIN, AUTHORITY)
     */
    @GetMapping
    public String listReports(@RequestParam(required = false) String type, Model model, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canGenerateReports(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        List<Report> reports;
        if (type != null && !type.isEmpty()) {
            reports = reportService.getReportsByType(type);
        } else {
            reports = reportService.getAllReports();
        }
        model.addAttribute("reports", reports);
        model.addAttribute("reportTypes", List.of("SYSTEM_SUMMARY", "DISASTER_SUMMARY", 
                "DONATION_SUMMARY", "ALLOCATION_SUMMARY"));
        model.addAttribute("currentType", type);
        model.addAttribute("loggedInUser", loggedInUser);
        return "reports/list";
    }

    /**
     * Show form to generate new report (ADMIN, AUTHORITY)
     */
    @GetMapping("/new")
    public String showGenerateForm(Model model, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canGenerateReports(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        model.addAttribute("disasters", disasterEventService.getAllDisasterEvents());
        model.addAttribute("reportTypes", List.of("SYSTEM_SUMMARY", "DISASTER_SUMMARY", 
                "DONATION_SUMMARY", "ALLOCATION_SUMMARY"));
        model.addAttribute("loggedInUser", loggedInUser);
        return "reports/form";
    }

    /**
     * Generate a new report (ADMIN, AUTHORITY)
     */
    @PostMapping
    public String generateReport(@RequestParam String reportType,
                                  @RequestParam(required = false) Integer disasterId,
                                  @RequestParam(required = false) String startDate,
                                  @RequestParam(required = false) String endDate,
                                  HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canGenerateReports(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }

        LocalDate start = startDate != null && !startDate.isEmpty() 
                ? LocalDate.parse(startDate) : LocalDate.now().minusMonths(1);
        LocalDate end = endDate != null && !endDate.isEmpty() 
                ? LocalDate.parse(endDate) : LocalDate.now();

        Report report;
        switch (reportType) {
            case "DISASTER_SUMMARY":
                if (disasterId == null) {
                    return "redirect:/reports/new?error=Disaster ID required";
                }
                DisasterEvent event = disasterEventService.getDisasterEventById(disasterId)
                        .orElseThrow(() -> new IllegalArgumentException("Disaster not found"));
                report = reportService.generateDisasterReport(event, loggedInUser);
                break;
            case "DONATION_SUMMARY":
                report = reportService.generateDonationReport(start, end, loggedInUser);
                break;
            case "ALLOCATION_SUMMARY":
                report = reportService.generateAllocationReport(start, end, loggedInUser);
                break;
            case "SYSTEM_SUMMARY":
            default:
                report = reportService.generateSystemReport(start, end, loggedInUser);
                break;
        }

        return "redirect:/reports/" + report.getReportId() + "?generated";
    }

    /**
     * View a specific report (ADMIN, AUTHORITY)
     */
    @GetMapping("/{id}")
    public String viewReport(@PathVariable Integer id, Model model, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!canGenerateReports(loggedInUser)) {
            return "redirect:/dashboard?accessDenied";
        }
        
        Report report = reportService.getReportById(id).orElse(null);
        model.addAttribute("report", report);
        model.addAttribute("loggedInUser", loggedInUser);
        return "reports/view";
    }

    /**
     * Export report in specified format (ADMIN, AUTHORITY)
     */
    @GetMapping("/{id}/export")
    @ResponseBody
    public ResponseEntity<String> exportReport(@PathVariable Integer id,
                                                @RequestParam(defaultValue = "TEXT") String format,
                                                HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null || !canGenerateReports(loggedInUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        try {
            String content = reportService.exportReport(id, format);
            
            HttpHeaders headers = new HttpHeaders();
            String filename = "report_" + id;
            
            switch (format.toUpperCase()) {
                case "HTML":
                    headers.setContentType(MediaType.TEXT_HTML);
                    filename += ".html";
                    break;
                case "CSV":
                    headers.setContentType(MediaType.parseMediaType("text/csv"));
                    filename += ".csv";
                    break;
                default:
                    headers.setContentType(MediaType.TEXT_PLAIN);
                    filename += ".txt";
            }
            
            headers.setContentDispositionFormData("attachment", filename);
            return new ResponseEntity<>(content, headers, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a report (ADMIN only)
     */
    @PostMapping("/{id}/delete")
    public String deleteReport(@PathVariable Integer id, HttpSession session) {
        User loggedInUser = getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (loggedInUser.getRole() != UserRole.ADMIN) {
            return "redirect:/dashboard?accessDenied";
        }
        
        reportService.deleteReport(id);
        return "redirect:/reports?deleted";
    }

    // ==================== REST API ENDPOINTS ====================

    /**
     * Get all reports (API)
     */
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Report>> getAllReportsApi() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    /**
     * Get report by ID (API)
     */
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Report> getReportApi(@PathVariable Integer id) {
        return reportService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Generate system report (API)
     */
    @PostMapping("/api/system")
    @ResponseBody
    public ResponseEntity<Report> generateSystemReportApi(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        
        LocalDate start = startDate != null ? LocalDate.parse(startDate) : LocalDate.now().minusMonths(1);
        LocalDate end = endDate != null ? LocalDate.parse(endDate) : LocalDate.now();
        
        Report report = reportService.generateSystemReport(start, end, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }

    /**
     * Generate disaster report (API)
     */
    @PostMapping("/api/disaster/{eventId}")
    @ResponseBody
    public ResponseEntity<Report> generateDisasterReportApi(@PathVariable Integer eventId) {
        return disasterEventService.getDisasterEventById(eventId)
                .map(event -> {
                    Report report = reportService.generateDisasterReport(event, null);
                    return ResponseEntity.status(HttpStatus.CREATED).body(report);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete report (API)
     */
    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteReportApi(@PathVariable Integer id) {
        try {
            reportService.deleteReport(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

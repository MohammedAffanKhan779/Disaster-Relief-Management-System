package com.disaster.management.service.impl;

import com.disaster.management.model.entity.*;
import com.disaster.management.model.enums.AllocationStatus;
import com.disaster.management.model.enums.RequestStatus;
import com.disaster.management.repository.*;
import com.disaster.management.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of ReportService.
 * Generates various types of reports with content aggregation.
 */
@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final DisasterEventRepository disasterEventRepository;
    private final DonationRepository donationRepository;
    private final AllocationRepository allocationRepository;
    private final ResourceRequestRepository requestRepository;
    private final ResourceRepository resourceRepository;
    private final ReliefCenterRepository reliefCenterRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository,
                              DisasterEventRepository disasterEventRepository,
                              DonationRepository donationRepository,
                              AllocationRepository allocationRepository,
                              ResourceRequestRepository requestRepository,
                              ResourceRepository resourceRepository,
                              ReliefCenterRepository reliefCenterRepository) {
        this.reportRepository = reportRepository;
        this.disasterEventRepository = disasterEventRepository;
        this.donationRepository = donationRepository;
        this.allocationRepository = allocationRepository;
        this.requestRepository = requestRepository;
        this.resourceRepository = resourceRepository;
        this.reliefCenterRepository = reliefCenterRepository;
    }

    @Override
    public Report generateReport(Report report) {
        if (report.getGeneratedDate() == null) {
            report.setGeneratedDate(LocalDate.now());
        }
        report.generateReport();
        return reportRepository.save(report);
    }

    @Override
    public Report generateDisasterReport(DisasterEvent event, User generatedBy) {
        StringBuilder content = new StringBuilder();
        content.append("=== DISASTER EVENT REPORT ===\n\n");
        content.append("Event: ").append(event.getType()).append("\n");
        content.append("Location: ").append(event.getLocation()).append("\n");
        content.append("Severity: ").append(event.getSeverity()).append("\n");
        content.append("Status: ").append(event.getStatus()).append("\n");
        content.append("Date: ").append(event.getDate()).append("\n");
        content.append("Description: ").append(event.getDescription()).append("\n\n");

        // Add donation statistics for this event
        List<Donation> donations = donationRepository.findByDisasterEvent(event);
        content.append("--- DONATIONS ---\n");
        content.append("Total Donations: ").append(donations.size()).append("\n");
        double totalAmount = donations.stream()
                .filter(d -> d.getAmount() != null)
                .mapToDouble(Donation::getAmount)
                .sum();
        content.append("Total Amount: $").append(String.format("%.2f", totalAmount)).append("\n\n");

        Report report = new Report();
        report.setReportType("DISASTER_SUMMARY");
        report.setGeneratedDate(LocalDate.now());
        report.setDisasterEvent(event);
        report.setGeneratedBy(generatedBy);
        report.setContent(content.toString());
        report.generateReport();

        return reportRepository.save(report);
    }

    @Override
    public Report generateSystemReport(LocalDate startDate, LocalDate endDate, User generatedBy) {
        StringBuilder content = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        content.append("=== SYSTEM SUMMARY REPORT ===\n");
        content.append("Period: ").append(startDate.format(formatter))
               .append(" to ").append(endDate.format(formatter)).append("\n\n");

        // Disaster statistics
        List<DisasterEvent> allEvents = disasterEventRepository.findAll();
        content.append("--- DISASTERS ---\n");
        content.append("Total Events: ").append(allEvents.size()).append("\n");
        long activeEvents = allEvents.stream()
                .filter(e -> e.getStatus().name().equals("ACTIVE"))
                .count();
        content.append("Active Events: ").append(activeEvents).append("\n\n");

        // Relief Center statistics
        List<ReliefCenter> centers = reliefCenterRepository.findAll();
        content.append("--- RELIEF CENTERS ---\n");
        content.append("Total Centers: ").append(centers.size()).append("\n");
        int totalCapacity = centers.stream()
                .filter(c -> c.getCapacity() != null)
                .mapToInt(ReliefCenter::getCapacity)
                .sum();
        content.append("Total Capacity: ").append(totalCapacity).append(" people\n\n");

        // Resource statistics
        List<Resource> resources = resourceRepository.findAll();
        content.append("--- RESOURCES ---\n");
        content.append("Total Resource Types: ").append(resources.size()).append("\n");
        int totalQuantity = resources.stream()
                .filter(r -> r.getQuantity() != null)
                .mapToInt(Resource::getQuantity)
                .sum();
        content.append("Total Quantity: ").append(totalQuantity).append(" units\n\n");

        // Request statistics
        List<ResourceRequest> requests = requestRepository.findAll();
        content.append("--- RESOURCE REQUESTS ---\n");
        content.append("Total Requests: ").append(requests.size()).append("\n");
        long pendingRequests = requests.stream()
                .filter(r -> r.getStatus() == RequestStatus.PENDING)
                .count();
        content.append("Pending: ").append(pendingRequests).append("\n");
        long approvedRequests = requests.stream()
                .filter(r -> r.getStatus() == RequestStatus.APPROVED)
                .count();
        content.append("Approved: ").append(approvedRequests).append("\n\n");

        // Allocation statistics
        List<Allocation> allocations = allocationRepository.findAll();
        content.append("--- ALLOCATIONS ---\n");
        content.append("Total Allocations: ").append(allocations.size()).append("\n");
        long deliveredAllocations = allocations.stream()
                .filter(a -> a.getStatus() == AllocationStatus.DELIVERED)
                .count();
        content.append("Delivered: ").append(deliveredAllocations).append("\n");
        long inTransitAllocations = allocations.stream()
                .filter(a -> a.getStatus() == AllocationStatus.IN_TRANSIT)
                .count();
        content.append("In Transit: ").append(inTransitAllocations).append("\n\n");

        Report report = new Report();
        report.setReportType("SYSTEM_SUMMARY");
        report.setGeneratedDate(LocalDate.now());
        report.setGeneratedBy(generatedBy);
        report.setContent(content.toString());
        report.generateReport();

        return reportRepository.save(report);
    }

    @Override
    public Report generateDonationReport(LocalDate startDate, LocalDate endDate, User generatedBy) {
        StringBuilder content = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        content.append("=== DONATIONS REPORT ===\n");
        content.append("Period: ").append(startDate.format(formatter))
               .append(" to ").append(endDate.format(formatter)).append("\n\n");

        List<Donation> donations = donationRepository.findByDateBetween(startDate, endDate);
        
        content.append("Total Donations: ").append(donations.size()).append("\n\n");

        // Group by type
        content.append("--- BY TYPE ---\n");
        donations.stream()
                .filter(d -> d.getType() != null)
                .collect(java.util.stream.Collectors.groupingBy(Donation::getType))
                .forEach((type, list) -> {
                    double sum = list.stream()
                            .filter(d -> d.getAmount() != null)
                            .mapToDouble(Donation::getAmount)
                            .sum();
                    content.append(type).append(": ").append(list.size())
                           .append(" donations, $").append(String.format("%.2f", sum)).append("\n");
                });

        content.append("\n--- RECENT DONATIONS ---\n");
        donations.stream()
                .limit(10)
                .forEach(d -> content.append("- ")
                        .append(d.getDate()).append(": ")
                        .append(d.getType()).append(" - $")
                        .append(d.getAmount()).append("\n"));

        Report report = new Report();
        report.setReportType("DONATION_SUMMARY");
        report.setGeneratedDate(LocalDate.now());
        report.setGeneratedBy(generatedBy);
        report.setContent(content.toString());
        report.generateReport();

        return reportRepository.save(report);
    }

    @Override
    public Report generateAllocationReport(LocalDate startDate, LocalDate endDate, User generatedBy) {
        StringBuilder content = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        content.append("=== ALLOCATIONS REPORT ===\n");
        content.append("Period: ").append(startDate.format(formatter))
               .append(" to ").append(endDate.format(formatter)).append("\n\n");

        List<Allocation> allocations = allocationRepository.findByAllocationDateBetween(startDate, endDate);
        
        content.append("Total Allocations: ").append(allocations.size()).append("\n\n");

        // Group by status
        content.append("--- BY STATUS ---\n");
        allocations.stream()
                .filter(a -> a.getStatus() != null)
                .collect(java.util.stream.Collectors.groupingBy(Allocation::getStatus))
                .forEach((status, list) -> {
                    int totalQty = list.stream()
                            .filter(a -> a.getAllocatedQuantity() != null)
                            .mapToInt(Allocation::getAllocatedQuantity)
                            .sum();
                    content.append(status).append(": ").append(list.size())
                           .append(" allocations, ").append(totalQty).append(" units\n");
                });

        content.append("\n--- RECENT ALLOCATIONS ---\n");
        allocations.stream()
                .limit(10)
                .forEach(a -> content.append("- ")
                        .append(a.getAllocationDate()).append(": ")
                        .append(a.getResource() != null ? a.getResource().getName() : "N/A")
                        .append(" x").append(a.getAllocatedQuantity())
                        .append(" [").append(a.getStatus()).append("]\n"));

        Report report = new Report();
        report.setReportType("ALLOCATION_SUMMARY");
        report.setGeneratedDate(LocalDate.now());
        report.setGeneratedBy(generatedBy);
        report.setContent(content.toString());
        report.generateReport();

        return reportRepository.save(report);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Report> getReportById(Integer reportId) {
        return reportRepository.findById(reportId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Report> getReportsByType(String reportType) {
        return reportRepository.findByReportType(reportType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Report> getReportsByEvent(DisasterEvent event) {
        return reportRepository.findByDisasterEvent(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Report> getReportsByDateRange(LocalDate startDate, LocalDate endDate) {
        return reportRepository.findByGeneratedDateBetween(startDate, endDate);
    }

    @Override
    public void deleteReport(Integer reportId) {
        reportRepository.deleteById(reportId);
    }

    @Override
    @Transactional(readOnly = true)
    public String exportReport(Integer reportId, String format) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("Report not found: " + reportId));

        String content = report.getContent();
        if (content == null) {
            content = "No content available";
        }

        return switch (format.toUpperCase()) {
            case "HTML" -> convertToHtml(report, content);
            case "CSV" -> convertToCsv(report, content);
            default -> content; // TEXT format
        };
    }

    private String convertToHtml(Report report, String content) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><title>")
            .append(report.getReportType())
            .append("</title></head><body>");
        html.append("<h1>").append(report.getReportType()).append("</h1>");
        html.append("<p>Generated: ").append(report.getGeneratedDate()).append("</p>");
        html.append("<pre>").append(content.replace("<", "&lt;").replace(">", "&gt;")).append("</pre>");
        html.append("</body></html>");
        return html.toString();
    }

    private String convertToCsv(Report report, String content) {
        StringBuilder csv = new StringBuilder();
        csv.append("Report Type,Generated Date\n");
        csv.append(report.getReportType()).append(",").append(report.getGeneratedDate()).append("\n\n");
        csv.append("Content\n");
        // Convert content lines to CSV-safe format
        String[] lines = content.split("\n");
        for (String line : lines) {
            csv.append("\"").append(line.replace("\"", "\"\"")).append("\"\n");
        }
        return csv.toString();
    }
}

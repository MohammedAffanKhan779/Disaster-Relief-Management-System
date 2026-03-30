package com.disaster.management.service;

import com.disaster.management.model.entity.DisasterEvent;
import com.disaster.management.model.entity.Report;
import com.disaster.management.model.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing reports.
 * Handles report generation, storage, and export.
 */
public interface ReportService {

    /**
     * Generate and save a new report.
     * @param report the report to generate
     * @return the saved report
     */
    Report generateReport(Report report);

    /**
     * Generate a disaster summary report.
     * @param event the disaster event
     * @param generatedBy the user generating the report
     * @return the generated report
     */
    Report generateDisasterReport(DisasterEvent event, User generatedBy);

    /**
     * Generate a system-wide summary report.
     * @param startDate report period start
     * @param endDate report period end
     * @param generatedBy the user generating the report
     * @return the generated report
     */
    Report generateSystemReport(LocalDate startDate, LocalDate endDate, User generatedBy);

    /**
     * Generate a donations summary report.
     * @param startDate report period start
     * @param endDate report period end
     * @param generatedBy the user generating the report
     * @return the generated report
     */
    Report generateDonationReport(LocalDate startDate, LocalDate endDate, User generatedBy);

    /**
     * Generate a resource allocation report.
     * @param startDate report period start
     * @param endDate report period end
     * @param generatedBy the user generating the report
     * @return the generated report
     */
    Report generateAllocationReport(LocalDate startDate, LocalDate endDate, User generatedBy);

    /**
     * Get a report by ID.
     * @param reportId the report ID
     * @return the report if found
     */
    Optional<Report> getReportById(Integer reportId);

    /**
     * Get all reports.
     * @return list of all reports
     */
    List<Report> getAllReports();

    /**
     * Get reports by type.
     * @param reportType the report type
     * @return list of matching reports
     */
    List<Report> getReportsByType(String reportType);

    /**
     * Get reports for a specific disaster event.
     * @param event the disaster event
     * @return list of reports for this event
     */
    List<Report> getReportsByEvent(DisasterEvent event);

    /**
     * Get reports within a date range.
     * @param startDate start date
     * @param endDate end date
     * @return list of reports in this range
     */
    List<Report> getReportsByDateRange(LocalDate startDate, LocalDate endDate);

    /**
     * Delete a report.
     * @param reportId the report ID
     */
    void deleteReport(Integer reportId);

    /**
     * Export report content as formatted string.
     * @param reportId the report ID
     * @param format export format (TEXT, HTML, CSV)
     * @return the formatted report content
     */
    String exportReport(Integer reportId, String format);
}

package com.disaster.management.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * MODEL CLASS - Report Entity
 * Represents a report generated for disaster events or system operations
 */
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Integer reportId;

    @NotBlank(message = "Report type is required")
    @Column(name = "report_type", nullable = false, length = 100)
    private String reportType;

    @NotNull(message = "Generated date is required")
    @Column(name = "generated_date", nullable = false)
    private LocalDate generatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private DisasterEvent disasterEvent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generated_by")
    private User generatedBy;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "file_path", length = 500)
    private String filePath;

    // Constructors
    public Report() {
    }

    public Report(Integer reportId, String reportType, LocalDate generatedDate, DisasterEvent disasterEvent,
                  User generatedBy, String content, String filePath) {
        this.reportId = reportId;
        this.reportType = reportType;
        this.generatedDate = generatedDate;
        this.disasterEvent = disasterEvent;
        this.generatedBy = generatedBy;
        this.content = content;
        this.filePath = filePath;
    }

    // Getters and Setters
    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public LocalDate getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(LocalDate generatedDate) {
        this.generatedDate = generatedDate;
    }

    public DisasterEvent getDisasterEvent() {
        return disasterEvent;
    }

    public void setDisasterEvent(DisasterEvent disasterEvent) {
        this.disasterEvent = disasterEvent;
    }

    public User getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(User generatedBy) {
        this.generatedBy = generatedBy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // Business methods
    public void generateReport() {
        this.generatedDate = LocalDate.now();
        System.out.println("Report generated: " + reportType);
    }

    public void exportReport() {
        System.out.println("Exporting report: " + reportType + " to " + filePath);
    }
}

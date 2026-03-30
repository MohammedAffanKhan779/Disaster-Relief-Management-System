package com.disaster.management.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * MODEL CLASS - Report Entity
 * Represents a report generated for disaster events or system operations
 */
@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    // Business methods
    public void generateReport() {
        this.generatedDate = LocalDate.now();
        System.out.println("Report generated: " + reportType);
    }

    public void exportReport() {
        System.out.println("Exporting report: " + reportType + " to " + filePath);
    }
}

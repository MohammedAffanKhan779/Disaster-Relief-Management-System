package com.disaster.management.model.entity;

import com.disaster.management.model.enums.DisasterStatus;
import com.disaster.management.model.enums.DisasterType;
import com.disaster.management.model.enums.SeverityLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * MODEL CLASS - DisasterEvent Entity
 * Represents a disaster event in the system
 */
@Entity
@Table(name = "disaster_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisasterEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer eventId;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Disaster type is required")
    @Column(nullable = false, length = 20)
    private DisasterType type;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Severity level is required")
    @Column(nullable = false, length = 20)
    private SeverityLevel severity;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    @Column(nullable = false, length = 20)
    private DisasterStatus status;

    @NotBlank(message = "Location is required")
    @Column(nullable = false, length = 200)
    private String location;

    @NotNull(message = "Date is required")
    @Column(nullable = false)
    private LocalDate date;

    @Column(length = 500)
    private String description;

    // Business methods
    public void updateStatus(DisasterStatus newStatus) {
        this.status = newStatus;
        System.out.println("Disaster event status updated to: " + newStatus);
    }

    public String generateEventReport() {
        return "Disaster Report - Type: " + type + ", Location: " + location + 
               ", Severity: " + severity + ", Status: " + status;
    }
}

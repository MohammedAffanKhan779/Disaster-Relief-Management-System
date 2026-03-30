package com.disaster.management.model.entity;

import com.disaster.management.model.enums.DisasterStatus;
import com.disaster.management.model.enums.DisasterType;
import com.disaster.management.model.enums.SeverityLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * MODEL CLASS - DisasterEvent Entity
 * Represents a disaster event in the system
 */
@Entity
@Table(name = "disaster_events")
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

    @OneToMany(mappedBy = "disasterEvent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ReliefCenter> reliefCenters = new ArrayList<>();

    @OneToMany(mappedBy = "disasterEvent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Donation> donations = new ArrayList<>();

    @OneToMany(mappedBy = "disasterEvent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Report> reports = new ArrayList<>();

    // Constructors
    public DisasterEvent() {
    }

    public DisasterEvent(Integer eventId, DisasterType type, SeverityLevel severity, DisasterStatus status,
                         String location, LocalDate date, String description) {
        this.eventId = eventId;
        this.type = type;
        this.severity = severity;
        this.status = status;
        this.location = location;
        this.date = date;
        this.description = description;
    }

    // Getters and Setters
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public DisasterType getType() {
        return type;
    }

    public void setType(DisasterType type) {
        this.type = type;
    }

    public SeverityLevel getSeverity() {
        return severity;
    }

    public void setSeverity(SeverityLevel severity) {
        this.severity = severity;
    }

    public DisasterStatus getStatus() {
        return status;
    }

    public void setStatus(DisasterStatus status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ReliefCenter> getReliefCenters() {
        return reliefCenters;
    }

    public void setReliefCenters(List<ReliefCenter> reliefCenters) {
        this.reliefCenters = reliefCenters;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

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

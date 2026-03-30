package com.disaster.management.model.entity;

import com.disaster.management.model.enums.DonationType;
import com.disaster.management.model.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

/**
 * MODEL CLASS - Donation Entity
 * Represents a donation made by a donor
 */
@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_id")
    private Integer donationId;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Donation type is required")
    @Column(nullable = false, length = 20)
    private DonationType type;

    @Positive(message = "Amount must be positive")
    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Payment method is required")
    @Column(name = "payment_method", nullable = false, length = 20)
    private PaymentMethod paymentMethod;

    @NotNull(message = "Date is required")
    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id", nullable = false)
    private Donor donor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private DisasterEvent disasterEvent;

    @Column(length = 500)
    private String description;

    @Column(name = "receipt_number", unique = true, length = 50)
    private String receiptNumber;

    // Constructors
    public Donation() {
    }

    public Donation(Integer donationId, DonationType type, Double amount, PaymentMethod paymentMethod,
                    LocalDate date, Donor donor, DisasterEvent disasterEvent, String description,
                    String receiptNumber) {
        this.donationId = donationId;
        this.type = type;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.donor = donor;
        this.disasterEvent = disasterEvent;
        this.description = description;
        this.receiptNumber = receiptNumber;
    }

    // Getters and Setters
    public Integer getDonationId() {
        return donationId;
    }

    public void setDonationId(Integer donationId) {
        this.donationId = donationId;
    }

    public DonationType getType() {
        return type;
    }

    public void setType(DonationType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public DisasterEvent getDisasterEvent() {
        return disasterEvent;
    }

    public void setDisasterEvent(DisasterEvent disasterEvent) {
        this.disasterEvent = disasterEvent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    // Business methods
    public void processDonation() {
        this.receiptNumber = "RCP-" + donationId + "-" + System.currentTimeMillis();
        System.out.println("Donation processed. Receipt: " + receiptNumber);
    }

    public String generateReceipt() {
        return "Receipt Number: " + receiptNumber + "\nDonor: " + donor.getName() + 
               "\nAmount: " + amount + "\nType: " + type + "\nDate: " + date;
    }
}

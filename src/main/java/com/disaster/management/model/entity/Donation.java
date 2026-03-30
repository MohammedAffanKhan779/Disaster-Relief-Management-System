package com.disaster.management.model.entity;

import com.disaster.management.model.enums.DonationType;
import com.disaster.management.model.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * MODEL CLASS - Donation Entity
 * Represents a donation made by a donor
 */
@Entity
@Table(name = "donations")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

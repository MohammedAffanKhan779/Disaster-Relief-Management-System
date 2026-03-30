package com.disaster.management.service.impl;

import com.disaster.management.model.entity.Allocation;
import com.disaster.management.model.entity.ReliefCenter;
import com.disaster.management.model.entity.Resource;
import com.disaster.management.model.enums.AllocationStatus;
import com.disaster.management.repository.AllocationRepository;
import com.disaster.management.repository.ResourceRepository;
import com.disaster.management.service.AllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of AllocationService.
 * Handles resource allocation lifecycle with automatic inventory updates on delivery.
 */
@Service
@Transactional
public class AllocationServiceImpl implements AllocationService {

    private final AllocationRepository allocationRepository;
    private final ResourceRepository resourceRepository;

    @Autowired
    public AllocationServiceImpl(AllocationRepository allocationRepository,
                                  ResourceRepository resourceRepository) {
        this.allocationRepository = allocationRepository;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Allocation createAllocation(Allocation allocation) {
        // Set default values
        if (allocation.getAllocationDate() == null) {
            allocation.setAllocationDate(LocalDate.now());
        }
        if (allocation.getStatus() == null) {
            allocation.setStatus(AllocationStatus.SCHEDULED);
        }
        allocation.allocateResource(); // Call entity business method
        return allocationRepository.save(allocation);
    }

    @Override
    public Allocation updateAllocationStatus(Integer allocationId, AllocationStatus newStatus) {
        Allocation allocation = allocationRepository.findById(allocationId)
                .orElseThrow(() -> new IllegalArgumentException("Allocation not found: " + allocationId));

        AllocationStatus currentStatus = allocation.getStatus();

        // Validate status transitions
        if (!isValidTransition(currentStatus, newStatus)) {
            throw new IllegalStateException(
                    "Invalid status transition from " + currentStatus + " to " + newStatus);
        }

        allocation.setStatus(newStatus);

        // When allocation is delivered, update the destination center's inventory
        if (newStatus == AllocationStatus.DELIVERED) {
            updateResourceInventory(allocation);
        }

        allocation.trackAllocation();
        return allocationRepository.save(allocation);
    }

    /**
     * Validates status transitions:
     * SCHEDULED -> IN_TRANSIT -> DELIVERED
     * SCHEDULED -> CANCELLED
     */
    private boolean isValidTransition(AllocationStatus from, AllocationStatus to) {
        if (from == null) return true;
        
        return switch (from) {
            case SCHEDULED -> to == AllocationStatus.IN_TRANSIT || to == AllocationStatus.CANCELLED;
            case IN_TRANSIT -> to == AllocationStatus.DELIVERED;
            case DELIVERED, CANCELLED -> false; // Terminal states
        };
    }

    /**
     * Updates resource inventory at the destination relief center when allocation is delivered.
     */
    private void updateResourceInventory(Allocation allocation) {
        Resource resource = allocation.getResource();
        ReliefCenter targetCenter = allocation.getReliefCenter();
        Integer quantity = allocation.getAllocatedQuantity();

        // Check if the resource is already at this center
        if (resource.getReliefCenter() != null && 
            resource.getReliefCenter().getCenterId().equals(targetCenter.getCenterId())) {
            // Same center - increase quantity
            resource.setQuantity(resource.getQuantity() + quantity);
        } else {
            // Different center - this is a transfer, just track it
            // The source would have already reduced its inventory when allocation was created
            resource.setQuantity(resource.getQuantity() + quantity);
        }
        
        resource.setReliefCenter(targetCenter);
        resource.updateQuantity(resource.getQuantity());
        resourceRepository.save(resource);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Allocation> getAllocationById(Integer allocationId) {
        return allocationRepository.findById(allocationId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Allocation> getAllAllocations() {
        return allocationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Allocation> getAllocationsByStatus(AllocationStatus status) {
        return allocationRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Allocation> getAllocationsByCenter(ReliefCenter reliefCenter) {
        return allocationRepository.findByReliefCenter(reliefCenter);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Allocation> getAllocationsByResource(Resource resource) {
        return allocationRepository.findAll().stream()
                .filter(a -> a.getResource().getResourceId().equals(resource.getResourceId()))
                .toList();
    }

    @Override
    public Allocation cancelAllocation(Integer allocationId) {
        Allocation allocation = allocationRepository.findById(allocationId)
                .orElseThrow(() -> new IllegalArgumentException("Allocation not found: " + allocationId));

        if (allocation.getStatus() != AllocationStatus.SCHEDULED) {
            throw new IllegalStateException("Only scheduled allocations can be cancelled");
        }

        allocation.setStatus(AllocationStatus.CANCELLED);
        return allocationRepository.save(allocation);
    }

    @Override
    public void deleteAllocation(Integer allocationId) {
        Allocation allocation = allocationRepository.findById(allocationId)
                .orElseThrow(() -> new IllegalArgumentException("Allocation not found: " + allocationId));

        if (allocation.getStatus() != AllocationStatus.CANCELLED) {
            throw new IllegalStateException("Only cancelled allocations can be deleted");
        }

        allocationRepository.deleteById(allocationId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getInTransitCount() {
        return allocationRepository.findByStatus(AllocationStatus.IN_TRANSIT).size();
    }
}

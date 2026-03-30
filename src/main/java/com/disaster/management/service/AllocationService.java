package com.disaster.management.service;

import com.disaster.management.model.entity.Allocation;
import com.disaster.management.model.entity.ReliefCenter;
import com.disaster.management.model.entity.Resource;
import com.disaster.management.model.enums.AllocationStatus;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing resource allocations.
 * Handles allocation lifecycle: creation, status updates, and tracking.
 */
public interface AllocationService {

    /**
     * Create a new allocation.
     * @param allocation the allocation to create
     * @return the saved allocation
     */
    Allocation createAllocation(Allocation allocation);

    /**
     * Update allocation status (SCHEDULED -> IN_TRANSIT -> DELIVERED).
     * When status becomes DELIVERED, resource quantity is updated.
     * @param allocationId the allocation ID
     * @param newStatus the new status
     * @return the updated allocation
     */
    Allocation updateAllocationStatus(Integer allocationId, AllocationStatus newStatus);

    /**
     * Get allocation by ID.
     * @param allocationId the allocation ID
     * @return the allocation if found
     */
    Optional<Allocation> getAllocationById(Integer allocationId);

    /**
     * Get all allocations.
     * @return list of all allocations
     */
    List<Allocation> getAllAllocations();

    /**
     * Get allocations by status.
     * @param status the status to filter by
     * @return list of matching allocations
     */
    List<Allocation> getAllocationsByStatus(AllocationStatus status);

    /**
     * Get allocations for a specific relief center.
     * @param reliefCenter the relief center
     * @return list of allocations for this center
     */
    List<Allocation> getAllocationsByCenter(ReliefCenter reliefCenter);

    /**
     * Get allocations for a specific resource.
     * @param resource the resource
     * @return list of allocations for this resource
     */
    List<Allocation> getAllocationsByResource(Resource resource);

    /**
     * Cancel an allocation (only if SCHEDULED).
     * @param allocationId the allocation ID
     * @return the cancelled allocation
     */
    Allocation cancelAllocation(Integer allocationId);

    /**
     * Delete an allocation (only if CANCELLED).
     * @param allocationId the allocation ID
     */
    void deleteAllocation(Integer allocationId);

    /**
     * Get count of in-transit allocations.
     * @return number of in-transit allocations
     */
    long getInTransitCount();
}

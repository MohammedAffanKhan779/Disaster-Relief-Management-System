package com.disaster.management.patterns.builder;

import com.disaster.management.model.entity.Allocation;
import com.disaster.management.model.entity.ReliefCenter;
import com.disaster.management.model.entity.Resource;
import com.disaster.management.model.entity.ResourceRequest;
import com.disaster.management.model.enums.AllocationStatus;

import java.time.LocalDate;

/**
 * Builder pattern for constructing Allocation instances with a fluent API.
 */
public class AllocationBuilder {

    private final Allocation allocation;

    public AllocationBuilder() {
        this.allocation = new Allocation();
    }

    public AllocationBuilder withAllocationDate(LocalDate allocationDate) {
        allocation.setAllocationDate(allocationDate);
        return this;
    }

    public AllocationBuilder withStatus(AllocationStatus status) {
        allocation.setStatus(status);
        return this;
    }

    public AllocationBuilder withResource(Resource resource) {
        allocation.setResource(resource);
        return this;
    }

    public AllocationBuilder withReliefCenter(ReliefCenter reliefCenter) {
        allocation.setReliefCenter(reliefCenter);
        return this;
    }

    public AllocationBuilder withResourceRequest(ResourceRequest resourceRequest) {
        allocation.setResourceRequest(resourceRequest);
        return this;
    }

    public AllocationBuilder withAllocatedQuantity(Integer allocatedQuantity) {
        allocation.setAllocatedQuantity(allocatedQuantity);
        return this;
    }

    public AllocationBuilder withNotes(String notes) {
        allocation.setNotes(notes);
        return this;
    }

    public Allocation build() {
        return allocation;
    }
}

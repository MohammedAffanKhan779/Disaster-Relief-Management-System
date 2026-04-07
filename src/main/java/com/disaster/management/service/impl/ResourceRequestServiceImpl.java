package com.disaster.management.service.impl;

import com.disaster.management.model.entity.Administrator;
import com.disaster.management.model.entity.Allocation;
import com.disaster.management.model.entity.ReliefCenter;
import com.disaster.management.model.entity.Resource;
import com.disaster.management.model.entity.ResourceRequest;
import com.disaster.management.model.enums.AllocationStatus;
import com.disaster.management.model.enums.RequestStatus;
import com.disaster.management.patterns.builder.AllocationBuilder;
import com.disaster.management.repository.AllocationRepository;
import com.disaster.management.repository.ResourceRequestRepository;
import com.disaster.management.service.ResourceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of ResourceRequestService.
 * Handles resource request lifecycle with automatic allocation creation on approval.
 */
@Service
@Transactional
public class ResourceRequestServiceImpl implements ResourceRequestService {

    private final ResourceRequestRepository requestRepository;
    private final AllocationRepository allocationRepository;

    @Autowired
    public ResourceRequestServiceImpl(ResourceRequestRepository requestRepository,
                                       AllocationRepository allocationRepository) {
        this.requestRepository = requestRepository;
        this.allocationRepository = allocationRepository;
    }

    @Override
    public ResourceRequest submitRequest(ResourceRequest request) {
        // Set default values for new request
        if (request.getRequestDate() == null) {
            request.setRequestDate(LocalDate.now());
        }
        request.setStatus(RequestStatus.PENDING);
        request.submitRequest(); // Call entity business method
        return requestRepository.save(request);
    }

    @Override
    public ResourceRequest approveRequest(Integer requestId) {
        return approveRequest(requestId, null);
    }

    public ResourceRequest approveRequest(Integer requestId, Administrator approver) {
        ResourceRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found: " + requestId));

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new IllegalStateException("Only pending requests can be approved");
        }

        // Update request status and approver
        request.setStatus(RequestStatus.APPROVED);
        if (approver != null) {
            request.setApprovedBy(approver);
        }
        request.approveRequest();

        // Create allocation automatically and link it to the request
        Allocation allocation = new AllocationBuilder()
                .withAllocationDate(LocalDate.now())
                .withStatus(AllocationStatus.SCHEDULED)
                .withResource(request.getResource())
                .withReliefCenter(request.getReliefCenter())
                .withAllocatedQuantity(request.getRequestedQuantity())
                .withResourceRequest(request)
                .withNotes("Auto-created from approved request #" + requestId)
                .build();
        allocation.allocateResource();
        allocationRepository.save(allocation);

        return requestRepository.save(request);
    }

    @Override
    public ResourceRequest rejectRequest(Integer requestId, String reason) {
        ResourceRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found: " + requestId));

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new IllegalStateException("Only pending requests can be rejected");
        }

        request.setStatus(RequestStatus.REJECTED);
        if (reason != null && !reason.isEmpty()) {
            String existingDesc = request.getDescription() != null ? request.getDescription() : "";
            request.setDescription(existingDesc + " [Rejected: " + reason + "]");
        }

        return requestRepository.save(request);
    }

    @Override
    public ResourceRequest fulfillRequest(Integer requestId) {
        ResourceRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found: " + requestId));

        if (request.getStatus() != RequestStatus.APPROVED) {
            throw new IllegalStateException("Only approved requests can be fulfilled");
        }

        request.setStatus(RequestStatus.FULFILLED);
        return requestRepository.save(request);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResourceRequest> getRequestById(Integer requestId) {
        return requestRepository.findById(requestId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceRequest> getRequestsByStatus(RequestStatus status) {
        return requestRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceRequest> getRequestsByCenter(ReliefCenter reliefCenter) {
        return requestRepository.findByReliefCenter(reliefCenter);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceRequest> getRequestsByResource(Resource resource) {
        return requestRepository.findAll().stream()
                .filter(r -> r.getResource().getResourceId().equals(resource.getResourceId()))
                .toList();
    }

    @Override
    public void deleteRequest(Integer requestId) {
        ResourceRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found: " + requestId));

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new IllegalStateException("Only pending requests can be deleted");
        }

        requestRepository.deleteById(requestId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getPendingRequestCount() {
        return requestRepository.findByStatus(RequestStatus.PENDING).size();
    }
}

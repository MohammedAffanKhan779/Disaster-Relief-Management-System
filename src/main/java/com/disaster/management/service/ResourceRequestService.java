package com.disaster.management.service;

import com.disaster.management.model.entity.ReliefCenter;
import com.disaster.management.model.entity.Resource;
import com.disaster.management.model.entity.ResourceRequest;
import com.disaster.management.model.enums.RequestStatus;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing resource requests.
 * Handles the complete lifecycle: submission, approval/rejection, and fulfillment.
 */
public interface ResourceRequestService {

    /**
     * Submit a new resource request.
     * @param request the resource request to submit
     * @return the saved request with generated ID
     */
    ResourceRequest submitRequest(ResourceRequest request);

    /**
     * Approve a pending resource request.
     * This will trigger allocation creation.
     * @param requestId the request ID to approve
     * @return the updated request
     */
    ResourceRequest approveRequest(Integer requestId);

    /**
     * Reject a pending resource request.
     * @param requestId the request ID to reject
     * @param reason optional rejection reason
     * @return the updated request
     */
    ResourceRequest rejectRequest(Integer requestId, String reason);

    /**
     * Mark a request as fulfilled after allocation is delivered.
     * @param requestId the request ID
     * @return the updated request
     */
    ResourceRequest fulfillRequest(Integer requestId);

    /**
     * Get a request by ID.
     * @param requestId the request ID
     * @return the request if found
     */
    Optional<ResourceRequest> getRequestById(Integer requestId);

    /**
     * Get all resource requests.
     * @return list of all requests
     */
    List<ResourceRequest> getAllRequests();

    /**
     * Get requests filtered by status.
     * @param status the status to filter by
     * @return list of matching requests
     */
    List<ResourceRequest> getRequestsByStatus(RequestStatus status);

    /**
     * Get requests for a specific relief center.
     * @param reliefCenter the relief center
     * @return list of requests for this center
     */
    List<ResourceRequest> getRequestsByCenter(ReliefCenter reliefCenter);

    /**
     * Get requests for a specific resource.
     * @param resource the resource
     * @return list of requests for this resource
     */
    List<ResourceRequest> getRequestsByResource(Resource resource);

    /**
     * Delete a request (only if pending).
     * @param requestId the request ID to delete
     */
    void deleteRequest(Integer requestId);

    /**
     * Get count of pending requests.
     * @return number of pending requests
     */
    long getPendingRequestCount();
}

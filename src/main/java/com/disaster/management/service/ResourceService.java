package com.disaster.management.service;

import com.disaster.management.model.entity.Resource;
import com.disaster.management.model.entity.ReliefCenter;
import com.disaster.management.model.enums.DonationType;

import java.util.List;
import java.util.Optional;

/**
 * SERVICE INTERFACE - Resource Service
 * Defines business logic operations for Resource management
 */
public interface ResourceService {
    
    Resource saveResource(Resource resource);
    
    Optional<Resource> getResourceById(Integer id);
    
    List<Resource> getAllResources();
    
    Resource updateResource(Resource resource);
    
    void deleteResource(Integer id);
    
    List<Resource> getResourcesByType(DonationType type);
    
    List<Resource> getResourcesByCenter(ReliefCenter center);
    
    List<Resource> getLowStockResources(Integer threshold);

    /**
     * Increase resource quantity (e.g., from donation)
     * @param resourceId the resource ID
     * @param quantity amount to add
     * @return the updated resource
     */
    Resource increaseQuantity(Integer resourceId, Integer quantity);

    /**
     * Decrease resource quantity (e.g., from allocation)
     * @param resourceId the resource ID
     * @param quantity amount to subtract
     * @return the updated resource
     * @throws IllegalStateException if insufficient quantity
     */
    Resource decreaseQuantity(Integer resourceId, Integer quantity);

    /**
     * Find or create a resource by name and type at a relief center
     * @param name resource name
     * @param type resource type
     * @param center relief center (optional)
     * @return existing or new resource
     */
    Resource findOrCreateResource(String name, DonationType type, ReliefCenter center);
}

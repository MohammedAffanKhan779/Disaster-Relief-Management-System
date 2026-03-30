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
}

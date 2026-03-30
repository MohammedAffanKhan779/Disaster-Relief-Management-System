package com.disaster.management.service.impl;

import com.disaster.management.model.entity.Resource;
import com.disaster.management.model.entity.ReliefCenter;
import com.disaster.management.model.enums.DonationType;
import com.disaster.management.repository.ResourceRepository;
import com.disaster.management.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * SERVICE IMPLEMENTATION - Resource Service
 * Implements business logic for Resource management with quantity tracking
 */
@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public Resource saveResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    @Override
    public Optional<Resource> getResourceById(Integer id) {
        return resourceRepository.findById(id);
    }

    @Override
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    @Override
    public Resource updateResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    @Override
    public void deleteResource(Integer id) {
        resourceRepository.deleteById(id);
    }

    @Override
    public List<Resource> getResourcesByType(DonationType type) {
        return resourceRepository.findByType(type);
    }

    @Override
    public List<Resource> getResourcesByCenter(ReliefCenter center) {
        return resourceRepository.findByReliefCenter(center);
    }

    @Override
    public List<Resource> getLowStockResources(Integer threshold) {
        return resourceRepository.findByQuantityLessThan(threshold);
    }

    @Override
    public Resource increaseQuantity(Integer resourceId, Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new IllegalArgumentException("Resource not found: " + resourceId));
        
        int newQuantity = resource.getQuantity() + quantity;
        resource.updateQuantity(newQuantity);
        return resourceRepository.save(resource);
    }

    @Override
    public Resource decreaseQuantity(Integer resourceId, Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new IllegalArgumentException("Resource not found: " + resourceId));
        
        if (resource.getQuantity() < quantity) {
            throw new IllegalStateException(
                    "Insufficient quantity. Available: " + resource.getQuantity() + ", Requested: " + quantity);
        }
        
        int newQuantity = resource.getQuantity() - quantity;
        resource.updateQuantity(newQuantity);
        return resourceRepository.save(resource);
    }

    @Override
    public Resource findOrCreateResource(String name, DonationType type, ReliefCenter center) {
        // Try to find existing resource with same name and type at same center
        List<Resource> existingResources = resourceRepository.findByNameContainingIgnoreCase(name);
        
        for (Resource existing : existingResources) {
            if (existing.getType() == type) {
                // Check if same center or no center specified
                if (center == null || 
                    (existing.getReliefCenter() != null && 
                     existing.getReliefCenter().getCenterId().equals(center.getCenterId()))) {
                    return existing;
                }
            }
        }
        
        // Create new resource
        Resource newResource = new Resource();
        newResource.setName(name);
        newResource.setType(type);
        newResource.setQuantity(0);
        newResource.setReliefCenter(center);
        newResource.setDescription("Auto-created from donation");
        
        return resourceRepository.save(newResource);
    }
}

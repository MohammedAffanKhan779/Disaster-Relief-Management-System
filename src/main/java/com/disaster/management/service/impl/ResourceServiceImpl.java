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
 * Implements business logic for Resource management
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
}

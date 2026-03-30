package com.disaster.management.service;

import com.disaster.management.model.entity.ReliefCenter;

import java.util.List;
import java.util.Optional;

/**
 * SERVICE INTERFACE - ReliefCenter Service
 * Business Logic Layer for ReliefCenter operations
 */
public interface ReliefCenterService {
    
    List<ReliefCenter> getAllReliefCenters();
    
    Optional<ReliefCenter> getReliefCenterById(Integer id);
    
    ReliefCenter saveReliefCenter(ReliefCenter reliefCenter);
    
    ReliefCenter updateReliefCenter(ReliefCenter reliefCenter);
    
    void deleteReliefCenter(Integer id);
    
    List<ReliefCenter> findByLocation(String location);
    
    List<ReliefCenter> findByMinCapacity(Integer capacity);
}

package com.disaster.management.service.impl;

import com.disaster.management.model.entity.ReliefCenter;
import com.disaster.management.repository.ReliefCenterRepository;
import com.disaster.management.service.ReliefCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * SERVICE IMPLEMENTATION - ReliefCenter Service
 * Business Logic Layer for ReliefCenter operations
 */
@Service
public class ReliefCenterServiceImpl implements ReliefCenterService {

    @Autowired
    private ReliefCenterRepository reliefCenterRepository;

    @Override
    public List<ReliefCenter> getAllReliefCenters() {
        return reliefCenterRepository.findAll();
    }

    @Override
    public Optional<ReliefCenter> getReliefCenterById(Integer id) {
        return reliefCenterRepository.findById(id);
    }

    @Override
    public ReliefCenter saveReliefCenter(ReliefCenter reliefCenter) {
        return reliefCenterRepository.save(reliefCenter);
    }

    @Override
    public ReliefCenter updateReliefCenter(ReliefCenter reliefCenter) {
        return reliefCenterRepository.save(reliefCenter);
    }

    @Override
    public void deleteReliefCenter(Integer id) {
        reliefCenterRepository.deleteById(id);
    }

    @Override
    public List<ReliefCenter> findByLocation(String location) {
        return reliefCenterRepository.findByLocation(location);
    }

    @Override
    public List<ReliefCenter> findByMinCapacity(Integer capacity) {
        return reliefCenterRepository.findByCapacityGreaterThanEqual(capacity);
    }
}

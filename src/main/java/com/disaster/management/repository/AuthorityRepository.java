package com.disaster.management.repository;

import com.disaster.management.model.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * REPOSITORY INTERFACE - Authority Repository
 * Data Access Layer for Authority entity
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    
    List<Authority> findByOrganization(String organization);
}

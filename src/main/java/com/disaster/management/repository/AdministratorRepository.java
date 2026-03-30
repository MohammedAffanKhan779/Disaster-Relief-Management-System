package com.disaster.management.repository;

import com.disaster.management.model.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * REPOSITORY INTERFACE - Administrator Repository
 * Data Access Layer for Administrator entity
 */
@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
}

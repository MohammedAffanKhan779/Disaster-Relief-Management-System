package com.disaster.management.repository;

import com.disaster.management.model.entity.User;
import com.disaster.management.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * REPOSITORY INTERFACE - User Repository
 * Data Access Layer for User entity
 * Extends JpaRepository for CRUD operations
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    // Custom query methods using Spring Data JPA naming conventions
    Optional<User> findByEmail(String email);
    
    List<User> findByRole(UserRole role);
    
    Optional<User> findByEmailAndPassword(String email, String password);
    
    boolean existsByEmail(String email);
}

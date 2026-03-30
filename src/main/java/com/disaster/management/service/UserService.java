package com.disaster.management.service;

import com.disaster.management.model.entity.User;
import com.disaster.management.model.enums.UserRole;

import java.util.List;
import java.util.Optional;

/**
 * SERVICE INTERFACE - User Service
 * Defines business logic operations for User management
 */
public interface UserService {
    
    User saveUser(User user);
    
    Optional<User> getUserById(Integer id);
    
    List<User> getAllUsers();
    
    User updateUser(User user);
    
    void deleteUser(Integer id);
    
    Optional<User> findByEmail(String email);
    
    List<User> getUsersByRole(UserRole role);
    
    Optional<User> authenticateUser(String email, String password);
    
    boolean emailExists(String email);
}

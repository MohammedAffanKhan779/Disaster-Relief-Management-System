package com.disaster.management.service.impl;

import com.disaster.management.model.entity.*;
import com.disaster.management.model.enums.AvailabilityStatus;
import com.disaster.management.model.enums.UserRole;
import com.disaster.management.patterns.factory.UserFactory;
import com.disaster.management.repository.UserRepository;
import com.disaster.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * SERVICE IMPLEMENTATION - User Service
 * Implements business logic for User management
 * This is part of the MODEL layer in MVC architecture
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        // Always persist as the correct subclass for the selected role
        User subclassUser = toSubclassEntity(user);
        return userRepository.save(subclassUser);
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        Optional<User> existingOpt = userRepository.findById(user.getUserId());
        if (existingOpt.isPresent()) {
            User existing = existingOpt.get();
            if (existing.getRole() == user.getRole()) {
                // Same role: update base fields on the already-correct subclass entity
                existing.setName(user.getName());
                existing.setPhone(user.getPhone());
                existing.setEmail(user.getEmail());
                if (user.getPassword() != null && !user.getPassword().isBlank()) {
                    existing.setPassword(user.getPassword());
                }
                return userRepository.save(existing);
            } else {
                // Role changed: remove old subclass row and persist a new subclass entity
                userRepository.deleteById(existing.getUserId());
                userRepository.flush();
                User newSubclass = toSubclassEntity(user);
                newSubclass.setUserId(null); // let the DB assign a fresh ID
                return userRepository.save(newSubclass);
            }
        }
        // Fallback for new entities arriving via the update path
        return userRepository.save(toSubclassEntity(user));
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    @Override
    public Optional<User> authenticateUser(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Converts a plain User (from form binding) into the correct JPA subclass
     * entity for the selected role, copying all base fields and applying safe
     * defaults for any subclass-specific required fields.
     */
    private User toSubclassEntity(User source) {
        User target = UserFactory.createUser(source.getRole());

        if (target instanceof Donor donor) {
            donor.setDonorType("Individual");
        } else if (target instanceof Volunteer volunteer) {
            volunteer.setSkillSet("General");
            volunteer.setAvailability(AvailabilityStatus.AVAILABLE);
        }

        target.setUserId(source.getUserId());
        target.setName(source.getName());
        target.setPhone(source.getPhone());
        target.setEmail(source.getEmail());
        target.setPassword(source.getPassword());
        target.setRole(source.getRole());
        return target;
    }
}

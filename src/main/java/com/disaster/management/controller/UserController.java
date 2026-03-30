package com.disaster.management.controller;

import com.disaster.management.model.entity.User;
import com.disaster.management.model.enums.UserRole;
import com.disaster.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTROLLER CLASS - User Controller
 * Handles HTTP requests for User operations
 * This is the CONTROLLER layer in MVC architecture
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Display all users
    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/list";
    }

    // Show registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", UserRole.values());
        return "users/register";
    }

    // Register new user
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userService.emailExists(user.getEmail())) {
            model.addAttribute("error", "Email already exists");
            return "users/register";
        }
        userService.saveUser(user);
        return "redirect:/users?success";
    }

    // Show login form
    @GetMapping("/login")
    public String showLoginForm() {
        return "users/login";
    }

    // Authenticate user
    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model) {
        var user = userService.authenticateUser(email, password);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "redirect:/dashboard";
        }
        model.addAttribute("error", "Invalid credentials");
        return "users/login";
    }

    // REST API Endpoints
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<User>> getUsersAPI() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<User> getUserByIdAPI(@PathVariable Integer id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<User> createUserAPI(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<User> updateUserAPI(@PathVariable Integer id, @RequestBody User user) {
        return userService.getUserById(id)
                .map(existingUser -> {
                    user.setUserId(id);
                    return ResponseEntity.ok(userService.updateUser(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteUserAPI(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

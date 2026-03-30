package com.disaster.management.controller;

import com.disaster.management.model.entity.User;
import com.disaster.management.model.enums.UserRole;
import com.disaster.management.service.UserService;
import jakarta.servlet.http.HttpSession;
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

    // Display all users (ADMIN only)
    @GetMapping
    public String getAllUsers(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (loggedInUser.getRole() != UserRole.ADMIN) {
            return "redirect:/dashboard?accessDenied";
        }
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("loggedInUser", loggedInUser);
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
            model.addAttribute("roles", UserRole.values());
            return "users/register";
        }
        userService.saveUser(user);
        return "redirect:/users/login?registered";
    }

    // Show login form
    @GetMapping("/login")
    public String showLoginForm(HttpSession session) {
        // If already logged in, redirect to dashboard
        if (session.getAttribute("loggedInUser") != null) {
            return "redirect:/dashboard";
        }
        return "users/login";
    }

    // Authenticate user and store in session
    @PostMapping("/login")
    public String loginUser(@RequestParam String email, 
                            @RequestParam String password, 
                            Model model,
                            HttpSession session) {
        var userOpt = userService.authenticateUser(email, password);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            session.setAttribute("loggedInUser", user);
            
            // Role-based dashboard redirect
            return switch (user.getRole()) {
                case ADMIN -> "redirect:/dashboard/admin";
                case DONOR -> "redirect:/dashboard/donor";
                case VOLUNTEER -> "redirect:/dashboard/volunteer";
                case RELIEF_STAFF -> "redirect:/dashboard/staff";
                case AUTHORITY -> "redirect:/dashboard/authority";
            };
        }
        model.addAttribute("error", "Invalid email or password");
        return "users/login";
    }

    // Logout user
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login?logout";
    }

    // View user profile (own profile or admin)
    @GetMapping("/{id}")
    public String viewUser(@PathVariable Integer id, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        // Allow viewing own profile or admin can view any
        if (!loggedInUser.getUserId().equals(id) && loggedInUser.getRole() != UserRole.ADMIN) {
            return "redirect:/dashboard?accessDenied";
        }
        userService.getUserById(id).ifPresent(user -> {
            model.addAttribute("user", user);
        });
        model.addAttribute("loggedInUser", loggedInUser);
        return "users/view";
    }

    // Show edit user form (own profile or admin)
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        // Allow editing own profile or admin can edit any
        if (!loggedInUser.getUserId().equals(id) && loggedInUser.getRole() != UserRole.ADMIN) {
            return "redirect:/dashboard?accessDenied";
        }
        userService.getUserById(id).ifPresent(user -> {
            model.addAttribute("user", user);
            model.addAttribute("roles", UserRole.values());
        });
        model.addAttribute("loggedInUser", loggedInUser);
        return "users/edit";
    }

    // Update user (own profile or admin)
    @PostMapping("/{id}")
    public String updateUser(@PathVariable Integer id, @ModelAttribute User user, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (!loggedInUser.getUserId().equals(id) && loggedInUser.getRole() != UserRole.ADMIN) {
            return "redirect:/dashboard?accessDenied";
        }
        user.setUserId(id);
        userService.updateUser(user);
        return "redirect:/users/" + id + "?updated";
    }

    // Delete user (ADMIN only)
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Integer id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        if (loggedInUser.getRole() != UserRole.ADMIN) {
            return "redirect:/dashboard?accessDenied";
        }
        userService.deleteUser(id);
        return "redirect:/users?deleted";
    }

    // View current user's profile
    @GetMapping("/profile")
    public String viewProfile(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        return "redirect:/users/" + loggedInUser.getUserId();
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

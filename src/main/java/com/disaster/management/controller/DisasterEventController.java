package com.disaster.management.controller;

import com.disaster.management.model.entity.DisasterEvent;
import com.disaster.management.model.enums.DisasterStatus;
import com.disaster.management.model.enums.DisasterType;
import com.disaster.management.model.enums.SeverityLevel;
import com.disaster.management.service.DisasterEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTROLLER CLASS - DisasterEvent Controller
 * Handles HTTP requests for Disaster Event operations
 */
@Controller
@RequestMapping("/disasters")
public class DisasterEventController {

    @Autowired
    private DisasterEventService disasterEventService;

    // Display all disaster events
    @GetMapping
    public String getAllDisasters(Model model) {
        List<DisasterEvent> disasters = disasterEventService.getAllDisasterEvents();
        model.addAttribute("disasters", disasters);
        return "disasters/list";
    }

    // Show form to create new disaster event
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("disaster", new DisasterEvent());
        model.addAttribute("types", DisasterType.values());
        model.addAttribute("severities", SeverityLevel.values());
        model.addAttribute("statuses", DisasterStatus.values());
        return "disasters/form";
    }

    // Create new disaster event
    @PostMapping
    public String createDisaster(@ModelAttribute DisasterEvent disaster) {
        disasterEventService.saveDisasterEvent(disaster);
        return "redirect:/disasters?success";
    }

    // View disaster details
    @GetMapping("/{id}")
    public String viewDisaster(@PathVariable Integer id, Model model) {
        disasterEventService.getDisasterEventById(id).ifPresent(disaster -> {
            model.addAttribute("disaster", disaster);
        });
        return "disasters/view";
    }

    // REST API Endpoints
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<DisasterEvent>> getDisastersAPI() {
        return ResponseEntity.ok(disasterEventService.getAllDisasterEvents());
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<DisasterEvent> getDisasterByIdAPI(@PathVariable Integer id) {
        return disasterEventService.getDisasterEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<DisasterEvent> createDisasterAPI(@RequestBody DisasterEvent disaster) {
        DisasterEvent saved = disasterEventService.saveDisasterEvent(disaster);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<DisasterEvent> updateDisasterAPI(@PathVariable Integer id, @RequestBody DisasterEvent disaster) {
        return disasterEventService.getDisasterEventById(id)
                .map(existing -> {
                    disaster.setEventId(id);
                    return ResponseEntity.ok(disasterEventService.updateDisasterEvent(disaster));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteDisasterAPI(@PathVariable Integer id) {
        disasterEventService.deleteDisasterEvent(id);
        return ResponseEntity.noContent().build();
    }
}

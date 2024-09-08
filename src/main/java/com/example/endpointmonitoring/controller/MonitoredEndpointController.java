package com.example.endpointmonitoring.controller;

import com.example.endpointmonitoring.model.MonitoredEndpoint;
import com.example.endpointmonitoring.model.User;
import com.example.endpointmonitoring.repository.MonitoredEndpointRepository;
import com.example.endpointmonitoring.repository.MonitoringResultRepository;
import com.example.endpointmonitoring.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpClient;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/endpoints")
public class MonitoredEndpointController {

    private MonitoredEndpointRepository endpointRepository;

    public MonitoredEndpointController(MonitoredEndpointRepository endpointRepository) {
        this.endpointRepository = endpointRepository;
    }

    @PostMapping
    public ResponseEntity<MonitoredEndpoint> createEndpoint(@RequestBody MonitoredEndpoint endpoint, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        endpoint.setOwner(user);
        endpoint.setCreatedAt(LocalDateTime.now());
        endpoint.setLastCheckedAt(null);
        MonitoredEndpoint savedEndpoint = endpointRepository.save(endpoint);
        return ResponseEntity.ok(savedEndpoint);
    }

    @GetMapping
    public ResponseEntity<List<MonitoredEndpoint>> getEndpoints(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        List<MonitoredEndpoint> endpoints = endpointRepository.findByOwner(user);
        return ResponseEntity.ok(endpoints);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonitoredEndpoint> updateEndpoint(@PathVariable Long id, @RequestBody MonitoredEndpoint updatedEndpoint, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        MonitoredEndpoint endpoint = endpointRepository.findByIdAndOwner(id, user);

        if (endpoint != null) {
            endpoint.setName(updatedEndpoint.getName());
            endpoint.setUrl(updatedEndpoint.getUrl());
            endpoint.setMonitoringInterval(updatedEndpoint.getMonitoringInterval());
            endpointRepository.save(endpoint);
            return ResponseEntity.ok(endpoint);
        }

        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEndpoint(@PathVariable Long id, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        MonitoredEndpoint endpoint = endpointRepository.findByIdAndOwner(id, user);

        if (endpoint != null) {
            endpointRepository.delete(endpoint);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(404).build();
    }
}


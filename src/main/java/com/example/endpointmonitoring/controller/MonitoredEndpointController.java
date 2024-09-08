package com.example.endpointmonitoring.controller;

import com.example.endpointmonitoring.dto.MonitoredEndpointRequest;
import com.example.endpointmonitoring.dto.MonitoredEndpointResponse;
import com.example.endpointmonitoring.model.MonitoredEndpoint;
import com.example.endpointmonitoring.service.MonitoredEndpointService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/endpoints")
@Validated
public class MonitoredEndpointController {

    private final MonitoredEndpointService monitoredEndpointService;

    public MonitoredEndpointController(
            MonitoredEndpointService monitoredEndpointService
    ) {
        this.monitoredEndpointService = monitoredEndpointService;
    }

    @PostMapping
    public ResponseEntity<MonitoredEndpointResponse> createEndpoint(
           @RequestHeader("Access-Token") String accessToken,
           @RequestBody MonitoredEndpointRequest endpoint
    ) {
        return ResponseEntity.ok(monitoredEndpointService.createMonitoredEndpoint(accessToken, endpoint));
    }

    @GetMapping
    public ResponseEntity<List<MonitoredEndpointResponse>> getEndpoints(
            @RequestHeader("Access-Token") String accessToken
    ) {
        List<MonitoredEndpointResponse> endpoints = monitoredEndpointService.getEndpointsResponse(accessToken);
        if (endpoints != null) {
            return ResponseEntity.ok(endpoints);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonitoredEndpointResponse> updateEndpoint(
            @PathVariable Long id,
            @RequestHeader("Access-Token") String accessToken,
            @RequestBody MonitoredEndpoint updatedEndpoint
    ) {
        MonitoredEndpointResponse endpoint = monitoredEndpointService.updateEndpoint(id, accessToken, updatedEndpoint);
        if (endpoint != null) {
            return ResponseEntity.ok(endpoint);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEndpoint(
            @PathVariable Long id,
            @RequestHeader("Access-Token") String accessToken
    ) {
        if (monitoredEndpointService.deleteEndpointSuccess(id, accessToken)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }
}


package com.example.endpointmonitoring.controller;

import com.example.endpointmonitoring.model.MonitoringResult;
import com.example.endpointmonitoring.service.MonitoringResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/results")
public class MonitoringResultController {

    private final MonitoringResultService monitoringResultService;

    public MonitoringResultController(
            MonitoringResultService monitoringResultService
    ) {
        this.monitoringResultService = monitoringResultService;
    }

    @GetMapping("/{endpointId}/last10")
    public ResponseEntity<List<MonitoringResult>> getLast10Results(
            @RequestHeader("Access-Token") String accessToken,
            @PathVariable Long endpointId
    ) {
        List<MonitoringResult> results = monitoringResultService.getLastTenResults(accessToken, endpointId);
        if (results != null) {
            return ResponseEntity.ok(results);
        } else {
            return ResponseEntity.status(404).build();
        }
    }
}

package com.example.endpointmonitoring.service;

import com.example.endpointmonitoring.model.MonitoredEndpoint;
import com.example.endpointmonitoring.model.MonitoringResult;
import com.example.endpointmonitoring.repository.MonitoredEndpointRepository;
import com.example.endpointmonitoring.repository.MonitoringResultRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class MonitoringService {

    private final MonitoredEndpointRepository endpointRepository;
    private final MonitoringResultRepository resultRepository;
    private final RestTemplate restTemplate;

    public MonitoringService(
            MonitoredEndpointRepository endpointRepository,
            MonitoringResultRepository resultRepository,
            RestTemplate restTemplate
    ) {
        this.endpointRepository = endpointRepository;
        this.resultRepository = resultRepository;
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedDelay = 10000)
    public void monitorEndpoints() {
        List<MonitoredEndpoint> endpoints = endpointRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (MonitoredEndpoint endpoint : endpoints) {
            if (endpoint.getLastCheckedAt() == null ||
                    now.minusSeconds(endpoint.getMonitoringInterval()).isAfter(endpoint.getLastCheckedAt())) {
                performMonitoring(endpoint);
            }
        }
    }

    private void performMonitoring(MonitoredEndpoint endpoint) {
        LocalDateTime checkedAt = LocalDateTime.now();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(endpoint.getUrl(), String.class);

            MonitoringResult result = new MonitoringResult();
            result.setCheckedAt(checkedAt);
            result.setStatusCode(response.getStatusCode().value());
            result.setPayload(response.getBody());
            result.setMonitoredEndpointId(endpoint.getId());

            resultRepository.save(result);

            endpoint.setId(endpoint.getId());
            endpoint.setLastCheckedAt(checkedAt);
            endpointRepository.save(endpoint);

        } catch(HttpClientErrorException | HttpServerErrorException exception) {
            MonitoringResult result = new MonitoringResult();
            result.setCheckedAt(checkedAt);
            result.setStatusCode(exception.getStatusCode().value());
            result.setPayload(exception.getMessage());
            result.setMonitoredEndpointId(endpoint.getId());

            resultRepository.save(result);

            endpoint.setId(endpoint.getId());
            endpoint.setLastCheckedAt(checkedAt);
            endpointRepository.save(endpoint);
            log.error("MonitoringService:performMonitoring Client/server error occurred while endpoint monitoring: {}", exception.getMessage());
        } catch (Exception e) {
            log.error("MonitoringService:performMonitoring Error occurred while endpoint monitoring: {}", e.getMessage());
        }
    }
}
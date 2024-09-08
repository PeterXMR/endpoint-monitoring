package com.example.endpointmonitoring.service;

import com.example.endpointmonitoring.model.MonitoredEndpoint;
import com.example.endpointmonitoring.model.MonitoringResult;
import com.example.endpointmonitoring.model.User;
import com.example.endpointmonitoring.repository.MonitoredEndpointRepository;
import com.example.endpointmonitoring.repository.MonitoringResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringResultService {

    private final MonitoringResultRepository resultRepository;
    private final MonitoredEndpointRepository endpointRepository;
    private final UserService userService;

    public MonitoringResultService(
            MonitoringResultRepository resultRepository,
            MonitoredEndpointRepository endpointRepository,
            UserService userService
    ) {
        this.resultRepository = resultRepository;
        this.endpointRepository = endpointRepository;
        this.userService = userService;
    }

    public List<MonitoringResult> getLastTenResults(
            String accessToken,
            Long endpointId
    ) {
        User user = userService.authenticateUser(accessToken);
        MonitoredEndpoint endpoint = endpointRepository.findByIdAndUser(endpointId, user);

        if (endpoint != null) {
            return resultRepository.findTop10ByMonitoredEndpointIdOrderByCheckedAtDesc(endpointId);
        } else {
            return null;
        }
    }
}

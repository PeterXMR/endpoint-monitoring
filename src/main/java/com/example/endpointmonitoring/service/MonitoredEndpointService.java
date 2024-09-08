package com.example.endpointmonitoring.service;

import com.example.endpointmonitoring.dto.MonitoredEndpointRequest;
import com.example.endpointmonitoring.dto.MonitoredEndpointResponse;
import com.example.endpointmonitoring.model.MonitoredEndpoint;
import com.example.endpointmonitoring.model.User;
import com.example.endpointmonitoring.repository.MonitoredEndpointRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonitoredEndpointService {

    private final ModelMapper modelMapper = new ModelMapper();
    private final MonitoredEndpointRepository endpointRepository;
    private final UserService userService;

    public MonitoredEndpointService(
            MonitoredEndpointRepository endpointRepository,
            UserService userService
    ) {
        this.endpointRepository = endpointRepository;
        this.userService = userService;
    }

    public User authenticateUser(
            String accessToken
    ) {
        return userService.authenticateUser(accessToken);
    }

    public MonitoredEndpointResponse  createMonitoredEndpoint(
            String accessToken,
            MonitoredEndpointRequest request
    ) {
        User user = authenticateUser(accessToken);
        request.setUser(user);
        request.setCreatedAt(LocalDateTime.now());
        request.setLastCheckedAt(null);
        return mapToResponse(endpointRepository.save(mapToEntity(request)));
    }

    public List<MonitoredEndpointResponse> getEndpointsResponse(String accessToken) {
        User user = authenticateUser(accessToken);
        List<MonitoredEndpoint> endpoints = endpointRepository.findByUser(user);
        return endpoints.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public MonitoredEndpoint mapToEntity(MonitoredEndpointRequest request) {
        return modelMapper.map(request, MonitoredEndpoint.class);
    }

    public MonitoredEndpointResponse mapToResponse(MonitoredEndpoint endpoint) {
        return modelMapper.map(endpoint, MonitoredEndpointResponse.class);
    }

    public MonitoredEndpointResponse updateEndpoint(
            Long id,
            String accessToken,
            MonitoredEndpoint updatedEndpoint
    ) {
        User user = authenticateUser(accessToken);
        MonitoredEndpoint endpoint = endpointRepository.findByIdAndUser(id, user);

        if (endpoint != null) {
            endpoint.setName(updatedEndpoint.getName());
            endpoint.setUrl(updatedEndpoint.getUrl());
            endpoint.setMonitoringInterval(updatedEndpoint.getMonitoringInterval());
            return mapToResponse(endpointRepository.save(endpoint));
        }
        return null;
    }

    public Boolean deleteEndpointSuccess(
            Long id,
            String accessToken
    ) {
        User user = authenticateUser(accessToken);
        MonitoredEndpoint endpoint = endpointRepository.findByIdAndUser(id, user);

        if (endpoint != null) {
            endpointRepository.delete(endpoint);
            return true;
        }
        return false;
    }
}

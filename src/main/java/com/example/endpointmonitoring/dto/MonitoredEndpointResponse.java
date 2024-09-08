package com.example.endpointmonitoring.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class MonitoredEndpointResponse {

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private LocalDateTime createdAt;

    @Getter
    @Setter
    private LocalDateTime lastCheckedAt;
    @Getter
    @Setter
    private Integer monitoringInterval; // in seconds
}

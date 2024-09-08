package com.example.endpointmonitoring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class MonitoredEndpointDto {

    private Long id;

    @NotBlank(message = "Name must not be blank")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "URL must not be blank")
    @Size(max = 100, message = "URL must not exceed 100 characters")
    private String url;

    @NotNull(message = "Created At must not be null")
    private LocalDateTime createdAt;

    private LocalDateTime lastCheckedAt;

    @NotNull(message = "Monitoring Interval must not be null")
    private Integer monitoringInterval;

    private UserDto user;
}

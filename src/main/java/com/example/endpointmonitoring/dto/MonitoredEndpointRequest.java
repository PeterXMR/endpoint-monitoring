package com.example.endpointmonitoring.dto;

import com.example.endpointmonitoring.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class MonitoredEndpointRequest {

    private Long id;

    @Getter
    @Setter
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @Getter
    @Setter
    @NotBlank(message = "URL cannot be blank")
    @Size(max = 100, message = "URL cannot exceed 100 characters")
    private String url;

    @Getter
    @Setter
    private LocalDateTime createdAt;

    @Getter
    @Setter
    private LocalDateTime lastCheckedAt;
    @Getter
    @Setter
    @NotNull(message = "Monitoring Interval cannot be null")
    private Integer monitoringInterval; // in seconds

    @Getter
    @Setter
    private User user;
}

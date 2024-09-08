package com.example.endpointmonitoring;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Entity
public class MonitoredEndpoint {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Getter
    private String url;
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime lastCheckedAt;
    private Integer monitoringInterval; // in seconds

    @ManyToOne
    private User owner;
}
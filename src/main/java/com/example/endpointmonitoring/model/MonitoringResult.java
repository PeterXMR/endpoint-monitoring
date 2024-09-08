package com.example.endpointmonitoring;


import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class MonitoringResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private LocalDateTime checkedAt;
    @Setter
    private Integer statusCode;

    @Setter
    @Lob
    private String payload;

    @Setter
    @ManyToOne
    private MonitoredEndpoint monitoredEndpoint;
}
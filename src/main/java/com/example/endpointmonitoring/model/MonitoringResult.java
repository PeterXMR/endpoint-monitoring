package com.example.endpointmonitoring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class MonitoringResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private LocalDateTime checkedAt;
    @Getter
    @Setter
    private Integer statusCode;

    @Getter
    @Setter
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String payload;

    @Setter
    private Long monitoredEndpointId;
}
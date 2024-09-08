package com.example.endpointmonitoring.repository;

import com.example.endpointmonitoring.model.MonitoringResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoringResultRepository extends JpaRepository<MonitoringResult, Long> {

    List<MonitoringResult> findTop10ByMonitoredEndpointIdOrderByCheckedAtDesc(Long monitoredEndpointId);
}

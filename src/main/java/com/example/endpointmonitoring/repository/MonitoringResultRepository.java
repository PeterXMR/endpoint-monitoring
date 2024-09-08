package com.example.endpointmonitoring.repository;


import com.example.endpointmonitoring.MonitoredEndpoint;
import com.example.endpointmonitoring.MonitoringResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoringResultRepository extends JpaRepository<MonitoringResult, Long> {

    // Fetch last 10 MonitoringResults for a specific MonitoredEndpoint
    List<MonitoringResult> findTop10ByMonitoredEndpointOrderByDateOfCheckDesc(MonitoredEndpoint monitoredEndpoint);
}

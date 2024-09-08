package com.example.endpointmonitoring.repository;

import com.example.endpointmonitoring.model.MonitoredEndpoint;
import com.example.endpointmonitoring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoredEndpointRepository extends JpaRepository<MonitoredEndpoint, Long> {

    List<MonitoredEndpoint> findByUser(User user);

    MonitoredEndpoint findByIdAndUser(Long id, User user);
}
package com.example.endpointmonitoring;

import com.example.endpointmonitoring.dto.MonitoredEndpointRequest;
import com.example.endpointmonitoring.model.MonitoredEndpoint;
import com.example.endpointmonitoring.service.MonitoredEndpointService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MonitoredEndpointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonitoredEndpointService monitoredEndpointService;

    @Autowired
    private ObjectMapper objectMapper;  // For JSON conversion

    @Test
    void testCreateEndpoint_Success() throws Exception {
        // Mock request and response data
        MonitoredEndpointRequest request = new MonitoredEndpointRequest();
        request.setName("Test Endpoint");
        request.setUrl("https://example.com");

        MonitoredEndpoint response = new MonitoredEndpoint();
        response.setId(1L);
        response.setName("Test Endpoint");
        response.setUrl("https://example.com");

        // Mock service call
        Mockito.when(monitoredEndpointService.createMonitoredEndpoint(anyString(), any(MonitoredEndpointRequest.class)))
                .thenReturn(response);

        // Perform POST request
        mockMvc.perform(post("/api/v1/endpoints")
                        .header("Access-Token", "test-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Endpoint"))
                .andExpect(jsonPath("$.url").value("https://example.com"));
    }

}


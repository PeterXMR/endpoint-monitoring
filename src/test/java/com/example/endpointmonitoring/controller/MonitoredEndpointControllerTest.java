package com.example.endpointmonitoring.controller;

import com.example.endpointmonitoring.dto.MonitoredEndpointRequest;
import com.example.endpointmonitoring.dto.MonitoredEndpointResponse;
import com.example.endpointmonitoring.model.MonitoredEndpoint;
import com.example.endpointmonitoring.service.MonitoredEndpointService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MonitoredEndpointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonitoredEndpointService monitoredEndpointService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "test-user")
    void testCreateEndpoint_Success() throws Exception {
        MonitoredEndpointRequest request = new MonitoredEndpointRequest();
        request.setName("Test Endpoint");
        request.setUrl("https://example.com");

        MonitoredEndpointResponse response = new MonitoredEndpointResponse();
        response.setId(1L);
        response.setName("Test Endpoint");
        response.setUrl("https://example.com");

        Mockito.when(monitoredEndpointService.createMonitoredEndpoint(anyString(), any(MonitoredEndpointRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/endpoints")
                        .header("Access-Token", "93f39e2f-80de-4033-99ee-249d92736a25")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test-user")
    void testGetEndpoints_Success() throws Exception {
        MonitoredEndpointResponse endpoint1 = new MonitoredEndpointResponse();
        endpoint1.setId(1L);
        endpoint1.setName("Test Endpoint 1");
        endpoint1.setUrl("https://example.com/1");

        MonitoredEndpointResponse endpoint2 = new MonitoredEndpointResponse();
        endpoint2.setId(2L);
        endpoint2.setName("Test Endpoint 2");
        endpoint2.setUrl("https://example.com/2");

        List<MonitoredEndpointResponse> endpoints = Arrays.asList(endpoint1, endpoint2);

        Mockito.when(monitoredEndpointService.getEndpointsResponse(anyString()))
                .thenReturn(endpoints);

        mockMvc.perform(get("/api/v1/endpoints")
                        .header("Access-Token", "93f39e2f-80de-4033-99ee-249d92736a25")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Endpoint 1"))
                .andExpect(jsonPath("$[0].url").value("https://example.com/1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Test Endpoint 2"))
                .andExpect(jsonPath("$[1].url").value("https://example.com/2"));
    }

    @Test
    @WithMockUser(username = "test-user")
    void testGetEndpoints_NotFound() throws Exception {
        Mockito.when(monitoredEndpointService.getEndpointsResponse(anyString()))
                .thenReturn(null);

        mockMvc.perform(get("/api/v1/endpoints")
                        .header("Access-Token", "invalid-access-token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test-user")
    void testUpdateEndpoint_Success() throws Exception {
        MonitoredEndpointResponse updatedEndpoint = new MonitoredEndpointResponse();
        updatedEndpoint.setId(1L);
        updatedEndpoint.setName("Updated Endpoint");
        updatedEndpoint.setUrl("https://updated-url.com");

        Mockito.when(monitoredEndpointService.updateEndpoint(anyLong(), anyString(), any(MonitoredEndpoint.class)))
                .thenReturn(updatedEndpoint);

        mockMvc.perform(put("/api/v1/endpoints/{id}", 1L)
                        .header("Access-Token", "93f39e2f-80de-4033-99ee-249d92736a25")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEndpoint)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Endpoint"))
                .andExpect(jsonPath("$.url").value("https://updated-url.com"));
    }

    @Test
    @WithMockUser(username = "test-user")
    void testDeleteEndpoint_Success() throws Exception {
        Mockito.when(monitoredEndpointService.deleteEndpointSuccess(anyLong(), anyString()))
                .thenReturn(true);

        mockMvc.perform(delete("/api/v1/endpoints/{id}", 1L)
                        .header("Access-Token", "93f39e2f-80de-4033-99ee-249d92736a25")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test-user")
    void testDeleteEndpoint_NotFound() throws Exception {
        Mockito.when(monitoredEndpointService.deleteEndpointSuccess(anyLong(), anyString()))
                .thenReturn(false);

        mockMvc.perform(delete("/api/v1/endpoints/{id}", 1L)
                        .header("Access-Token", "93f39e2f-80de-4033-99ee-249d92736a25")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}


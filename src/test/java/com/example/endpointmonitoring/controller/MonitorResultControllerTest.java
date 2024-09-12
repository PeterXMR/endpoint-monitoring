package com.example.endpointmonitoring.controller;

import com.example.endpointmonitoring.model.MonitoringResult;
import com.example.endpointmonitoring.service.MonitoringResultService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MonitorResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonitoringResultService monitoringResultService;

    @Test
    void testGetLastTenResults_Success() throws Exception {
        List<MonitoringResult> mockResults = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MonitoringResult result = new MonitoringResult();
            result.setStatusCode(200);
            result.setPayload("Mock payload " + i);
            mockResults.add(result);
        }

        Mockito.when(monitoringResultService.getLastTenResults(anyString(), anyLong()))
                .thenReturn(mockResults);

        mockMvc.perform(get("/api/v1/results/1/last10")
                        .header("Access-Token", "93f39e2f-80de-4033-99ee-249d92736a25")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10))
                .andExpect(jsonPath("$[0].statusCode").value(200))
                .andExpect(jsonPath("$[0].payload").value("Mock payload 0"));
    }

    @Test
    void testGetLastTenResults_NotFound() throws Exception {
        Mockito.when(monitoringResultService.getLastTenResults(anyString(), anyLong()))
                .thenReturn(null);

        mockMvc.perform(get("/api/v1/results/1/last10")
                        .header("Access-Token", "93f39e2f-80de-4033-99ee-249d92736a25")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetLastTenResults_EmptyResults() throws Exception {
        Mockito.when(monitoringResultService.getLastTenResults(anyString(), anyLong()))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/results/1/last10")
                        .header("Access-Token", "93f39e2f-80de-4033-99ee-249d92736a25")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}

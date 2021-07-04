package com.example.realtimestatisticsapi.statistics;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatisticsController.class)
@ActiveProfiles("test")
public class StatisticsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticsService service;

    @Test
    public void statisticsControllerGet_whenCalled_shouldCreatedStatus() throws Exception {
        when(service.getAggregatedStatistics()).thenReturn(new Statistics());
        String expectedContent = "{" +
                "\"sum\":\"0.00\"," +
                "\"avg\":\"0.00\"," +
                "\"max\":\"null\"," +
                "\"min\":\"null\"," +
                "\"count\":0" + "}";
        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedContent));
    }
}

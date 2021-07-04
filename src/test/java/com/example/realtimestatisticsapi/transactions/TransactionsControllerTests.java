package com.example.realtimestatisticsapi.transactions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionsController.class)
@ActiveProfiles("test")
public class TransactionsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionsService service;

    private final String validTransactionJson = "{ " +
            "\"amount\": \"12.3343\", " +
            "\"timestamp\": \"2020-07-17T09:59:51.312Z\"" +
            " }";

    @Test
    public void transactionControllerPost_whenCalledWithValidJson_shouldCreatedStatus() throws Exception {
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validTransactionJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void transactionControllerPost_whenCalledWithInValidJson_shouldReturnBadRequestStatus() throws Exception {
        String invalidTransactionJson = "";
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidTransactionJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void transactionControllerPost_whenCalledWithFutureTransaction_shouldReturnForbiddenStatus() throws Exception {
        String nextYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR) + 1);
        String validTransactionJson = String.format("{ \"amount\": \"12.3343\", \"timestamp\": \"%s-07-17T09:59:51.312Z\" }",
                nextYear);
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validTransactionJson))
                .andExpect(status().isForbidden());
    }

    @Test
    public void transactionControllerPost_whenCalledWithoutAmount_shouldReturnForbiddenStatus() throws Exception {
        String invalidTransactionJson = "{ \"timestamp\": \"2020-07-17T09:59:51.312Z\" }";
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidTransactionJson))
                .andExpect(status().isForbidden());
    }

    @Test
    public void transactionControllerPost_whenCalledOldTransaction_shouldReturnNoContentStatus() throws Exception {
        when(service.isTransactionOld(any())).thenReturn(true);
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validTransactionJson))
                .andExpect(status().isNoContent());
    }

    @Test
    public void transactionControllerDelete_whenCalled_shouldReturnNoContentStatus() throws Exception {
        mockMvc.perform(delete("/transactions"))
                .andExpect(status().isNoContent());
    }
}
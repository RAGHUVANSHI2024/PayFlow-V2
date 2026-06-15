package com.payflow.wallet;

import com.payflow.wallet.controller.TransactionController;
import com.payflow.wallet.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @MockitoBean
    private TransactionService transactionService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldTransactionSuccessfully() throws Exception {

        String request = """
                {
                "senderWalletId":1,
                "receiverWalletId":2,
                "amount":200                
                }
                """;

        mockMvc.perform(
                        post("/api/v1/transaction/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isOk());

        verify(transactionService).transfer(any());
    }

    @Test
    void shouldReturnBadRequest()throws Exception{
        String request = """
                {
                "senderWalletId":1,
                "receiverWalletId":2,
                "amount":0.0                
                }
                """;

        mockMvc.perform(
                        post("/api/v1/transaction/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(
                        status().isBadRequest()
                );
    }
}
package com.payflow.wallet;

import com.payflow.wallet.entity.Wallet;
import com.payflow.wallet.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private WalletRepository walletRepository;

    @Test
    void shouldCreateWallet() throws Exception {

        String request = """
    {
      "userId": 1,
      "balance": 1000
    }
    """;

        mockMvc.perform(
                        post("/api/v1/wallets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isCreated());

        List<Wallet> wallets =
                walletRepository.findAll();

        assertEquals(1, wallets.size());
    }

    @Test
    void contextLoads(){

    }
}

package com.payflow.wallet;

import com.payflow.wallet.entity.Wallet;
import com.payflow.wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class WalletRepositoryIntegrationTest {

    @Autowired
    private WalletRepository walletRepository;

    @Test
    void shouldSaveWallet() {

        Wallet wallet = Wallet.builder()
                .balance(new BigDecimal("1000"))
                .build();

        Wallet savedWallet =
                walletRepository.save(wallet);

        assertNotNull(savedWallet.getId());

        assertEquals(
                new BigDecimal("1000"),
                savedWallet.getBalance()
        );
    }

    @Test
    void shouldFindWalletById() {

        Wallet wallet = Wallet.builder()
                .balance(new BigDecimal("500"))
                .build();

        Wallet saved =
                walletRepository.save(wallet);

        Wallet found =
                walletRepository.findById(
                        saved.getId()
                ).orElseThrow();

        assertEquals(
                new BigDecimal("500"),
                found.getBalance()
        );
    }
}

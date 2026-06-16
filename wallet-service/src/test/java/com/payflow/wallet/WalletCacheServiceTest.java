package com.payflow.wallet;

import com.payflow.wallet.service.WalletCacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class WalletCacheServiceTest {

    @Autowired
    private WalletCacheService walletCacheService;


    @Test
    void shouldSaveAndGetBalance(){

        Long walletId = 1L;

        BigDecimal balance = new BigDecimal("5000");

        walletCacheService.saveBalance(
                walletId,
                balance
        );

        BigDecimal cacheBalance = walletCacheService
                .getBalance(walletId);

        assertEquals(balance, cacheBalance);
    }

    @Test
    void shouldEvictBalance(){

        Long walletId = 1L;

        walletCacheService.saveBalance(walletId,new BigDecimal("5000"));

        walletCacheService.evictBalance(walletId);

        BigDecimal balance = walletCacheService.getBalance(walletId);

        assertNull(balance);
    }
}

package com.payflow.wallet;


import com.payflow.wallet.Exception.InsufficientBalanceException;
import com.payflow.wallet.dto.TransferMoneyRequest;
import com.payflow.wallet.entity.Wallet;
import com.payflow.wallet.repository.TransactionRepository;
import com.payflow.wallet.repository.WalletRepository;
import com.payflow.wallet.service.TransactionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class TransferIntegrationTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;


    @Test
    void shouldTransferMoney() {

        Wallet sender = Wallet.builder()
                .balance(new BigDecimal("1000"))
                .build();

        Wallet receiver = Wallet.builder()
                .balance(new BigDecimal("500"))
                .build();

        sender = walletRepository.save(sender);
        receiver = walletRepository.save(receiver);

        TransferMoneyRequest request =
                new TransferMoneyRequest();

        request.setSenderWalletId(sender.getId());
        request.setReceiverWalletId(receiver.getId());
        request.setAmount(new BigDecimal("200"));

        transactionService.transfer(request);

        Wallet updatedSender =
                walletRepository.findById(sender.getId())
                        .orElseThrow();

        Wallet updatedReceiver =
                walletRepository.findById(receiver.getId())
                        .orElseThrow();

        assertEquals(
                new BigDecimal("800"),
                updatedSender.getBalance()
        );

        assertEquals(
                new BigDecimal("700"),
                updatedReceiver.getBalance()
        );

        assertEquals(
                1,
                transactionRepository.count()
        );
    }

    @Test
    void shouldThrowInsufficientBalanceException(){

        Wallet sender = Wallet.builder()
                .balance(new BigDecimal("100"))
                .build();

        Wallet receiver = Wallet.builder()
                .balance(new BigDecimal("500"))
                .build();

        sender = walletRepository.save(sender);
        receiver = walletRepository.save(receiver);


        TransferMoneyRequest request =
                new TransferMoneyRequest();

        request.setSenderWalletId(sender.getId());
        request.setReceiverWalletId(receiver.getId());
        request.setAmount(new BigDecimal("200"));

        assertThrows(InsufficientBalanceException.class, ()-> transactionService.transfer(request));

        Wallet updateSender  = walletRepository.findById(sender.getId()).orElseThrow();
        Wallet updateReceiver = walletRepository.findById(receiver.getId()).orElseThrow();

        assertEquals(new BigDecimal("100"),updateSender.getBalance());
        assertEquals(new BigDecimal("500"),updateReceiver.getBalance());

        assertEquals(0,transactionRepository.count());
    }
}
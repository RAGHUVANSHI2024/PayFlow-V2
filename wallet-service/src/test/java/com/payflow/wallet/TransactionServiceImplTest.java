package com.payflow.wallet;

import com.payflow.wallet.Exception.InsufficientBalanceException;
import com.payflow.wallet.dto.TransferMoneyRequest;
import com.payflow.wallet.entity.Transaction;
import com.payflow.wallet.entity.Wallet;
import com.payflow.wallet.repository.TransactionRepository;
import com.payflow.wallet.repository.WalletRepository;
import com.payflow.wallet.service.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void shouldTransferMoneySuccessfully(){

        Wallet sender = Wallet.builder()
                .id(1L)
                .balance(new BigDecimal("1000"))
                .build();

        Wallet receiver = Wallet.builder()
                .id(2L)
                .balance(new BigDecimal("500"))
                .build();


        TransferMoneyRequest request =
                new TransferMoneyRequest();

        request.setSenderWalletId(1L);
        request.setReceiverWalletId(2L);
        request.setAmount(
                new BigDecimal("200")
        );

        when(walletRepository.findById(1L))
                .thenReturn(Optional.of(sender));

        when(walletRepository.findById(2L))
                .thenReturn(Optional.of(receiver));

        transactionService.transfer(request);

        assertEquals(
                new BigDecimal("800"),
                sender.getBalance()
        );

        assertEquals(
                new BigDecimal("700"),
                receiver.getBalance()
        );

        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void shouldThrowExceptionWhenBalanceIsLow() {

        Wallet sender = Wallet.builder()
                .id(1L)
                .balance(new BigDecimal("100"))
                .build();

        Wallet receiver = Wallet.builder()
                .id(2L)
                .balance(new BigDecimal("500"))
                .build();

        TransferMoneyRequest request =
                new TransferMoneyRequest();

        request.setSenderWalletId(1L);
        request.setReceiverWalletId(2L);
        request.setAmount(
                new BigDecimal("200")
        );

        when(walletRepository.findById(1L))
                .thenReturn(Optional.of(sender));

        when(walletRepository.findById(2L))
                .thenReturn(Optional.of(receiver));

        assertThrows(
                InsufficientBalanceException.class,
                () -> transactionService.transfer(request)
        );

        verify(transactionRepository,
                never()).save(any());
    }
}

package com.payflow.wallet.service;

import com.payflow.wallet.dto.TransferMoneyRequest;
import com.payflow.wallet.entity.Transaction;
import com.payflow.wallet.entity.Wallet;
import com.payflow.wallet.enums.TransactionStatus;
import com.payflow.wallet.repository.TransactionRepository;
import com.payflow.wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final WalletRepository walletRepository;

    private final TransactionRepository transactionRepository;

    @Override
    public void transfer(TransferMoneyRequest request) {

        Wallet sender = walletRepository.findById(request
                .getSenderWalletId())
                .orElseThrow(() -> new RuntimeException("SenderId does not exist!"));

        Wallet receiver = walletRepository.findById(request
                        .getReceiverWalletId())
                .orElseThrow(() -> new RuntimeException("ReceiverId does not exist!"));


        if (sender.getBalance().compareTo(request.getAmount()) < 0) {

            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(
                sender.getBalance()
                        .subtract(request.getAmount()));

        receiver.setBalance(
                receiver.getBalance()
                        .add(request.getAmount()));

        walletRepository.save(sender);
        walletRepository.save(receiver);

        Transaction transaction = Transaction.builder()
                .senderWalletId(sender.getId())
                .senderWalletId(receiver.getId())
                .amount(request.getAmount())
                .status(TransactionStatus.SUCCESS)
                .createdAt(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

    }
}

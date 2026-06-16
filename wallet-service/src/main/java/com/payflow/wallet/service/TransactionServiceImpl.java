package com.payflow.wallet.service;

import com.payflow.wallet.Exception.InsufficientBalanceException;
import com.payflow.wallet.Exception.WalletIdNotFoundException;
import com.payflow.wallet.dto.TransferMoneyRequest;
import com.payflow.wallet.entity.Transaction;
import com.payflow.wallet.entity.Wallet;
import com.payflow.wallet.enums.TransactionStatus;
import com.payflow.wallet.repository.TransactionRepository;
import com.payflow.wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final WalletRepository walletRepository;

    private final TransactionRepository transactionRepository;

    private final WalletCacheService walletCacheService;
    @Override
    public void transfer(TransferMoneyRequest request) {

        Wallet sender = walletRepository.findById(request
                .getSenderWalletId())
                .orElseThrow(() -> new WalletIdNotFoundException("SenderId does not exist!"));

        Wallet receiver = walletRepository.findById(request
                        .getReceiverWalletId())
                .orElseThrow(() -> new WalletIdNotFoundException("ReceiverId does not exist!"));


        if (sender.getBalance().compareTo(request.getAmount()) < 0) {

            throw new InsufficientBalanceException("Insufficient balance");
        }

        sender.setBalance(
                sender.getBalance()
                        .subtract(request.getAmount()));

        receiver.setBalance(
                receiver.getBalance()
                        .add(request.getAmount()));

        walletCacheService.evictBalance(sender.getId());
        walletCacheService.evictBalance(receiver.getId());

        walletRepository.save(sender);
        walletRepository.save(receiver);

        Transaction transaction = Transaction.builder()
                .senderWalletId(sender.getId())
                .receiverWalletId(receiver.getId())
                .amount(request.getAmount())
                .status(TransactionStatus.SUCCESS)
                .build();

        transactionRepository.save(transaction);

    }

    @Override
    public Page<Transaction> getHistory(Long walletId, int page, int size) {

        Pageable pageable = PageRequest
                .of(
                        page,
                        size,
                        Sort.by("createdAt").descending()
                );

        return transactionRepository.findBySenderWalletIdOrReceiverWalletId(walletId,walletId,pageable);
    }
}

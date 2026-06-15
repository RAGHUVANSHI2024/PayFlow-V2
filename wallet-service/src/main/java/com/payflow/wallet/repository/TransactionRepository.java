package com.payflow.wallet.repository;

import com.payflow.wallet.entity.Transaction;
import com.payflow.wallet.enums.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    Page<Transaction> findBySenderWalletIdOrReceiverWalletId(
            Long senderWalletId,
            Long receiverWalletId,
            Pageable size
    );

    @Query("""
    SELECT t
    FROM Transaction t
    WHERE t.status = :status
    """)
    List<Transaction> findByStatus(@Param("status")TransactionStatus status);
}

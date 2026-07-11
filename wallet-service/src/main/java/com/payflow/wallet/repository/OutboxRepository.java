package com.payflow.wallet.repository;

import com.payflow.wallet.entity.OutboxEvent;
import com.payflow.wallet.enums.OutboxStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboxRepository extends JpaRepository<OutboxEvent,Long> {

    Page<OutboxEvent> findByStatusIn(
            List<OutboxStatus> status,
            Pageable pageable
    );
}

package com.payflow.wallet.controller;

import com.payflow.wallet.dto.TransferMoneyRequest;
import com.payflow.wallet.entity.Transaction;
import com.payflow.wallet.response.ApiResponse;
import com.payflow.wallet.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<Void>> transfer(
           @Valid @RequestBody TransferMoneyRequest request
    ){
        transactionService.transfer(request);

        ApiResponse response = ApiResponse.builder()
                .message("Money transferred successfully")
                .status(200)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/{walletId}")
    public ResponseEntity<ApiResponse<Page<Transaction>>> getHistory(@PathVariable Long walletId,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size){

        Page<Transaction> history = transactionService.getHistory(walletId, page, size);

        return ResponseEntity.ok(
                ApiResponse.<Page<Transaction>>builder()
                        .message("Transaction fetch successfully")
                        .status(200)
                        .data(history)
                        .build()
        );
    }
}

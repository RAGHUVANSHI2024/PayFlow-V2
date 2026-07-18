package com.payflow.wallet.controller;

import com.payflow.wallet.dto.TransferMoneyRequest;
import com.payflow.wallet.entity.Transaction;
import com.payflow.wallet.response.ApiResponse;
import com.payflow.wallet.service.TransactionService;
import io.swagger.v3.oas.annotations.OpenAPI31;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
@Tag(
        name = "Transaction Management",
        description = "Money transfer and transaction history APIs."
)
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    @Operation(
            summary = "Transfer Money",
            description = "Money transfer from one wallet to another"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Transfer initiated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid Request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Wallet Not Found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Insufficient balance")
    })
    public ResponseEntity<ApiResponse<Void>> transfer(@Valid @RequestBody TransferMoneyRequest request){
        transactionService.transfer(request);

        ApiResponse response = ApiResponse.builder()
                .message("Money transferred successfully")
                .status(200)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/{walletId}")
    @Operation(
            summary = "Get Wallet History",
            description = "Get all previous transaction"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Success"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid Request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Wallet Not Found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Parameter(
            description = "Wallet Id",
            example = "1"
    )
    public ResponseEntity<ApiResponse<Page<Transaction>>> getHistory(
                                        @PathVariable Long walletId,
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

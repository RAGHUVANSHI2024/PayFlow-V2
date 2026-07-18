package com.payflow.wallet.Exception;

import com.payflow.wallet.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiResponse<?>> handleInsufficientBalance(InsufficientBalanceException exception){

        ApiResponse response = ApiResponse.builder()
                .message(exception.getMessage())
                .status(400)
                .build();

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(WalletIdNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> walletIdNotExist(WalletIdNotFoundException exception){

        ApiResponse response = ApiResponse.builder()
                .message(exception.getMessage())
                .status(400)
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }
}

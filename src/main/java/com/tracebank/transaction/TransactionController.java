package com.tracebank.transaction;

import com.tracebank.transaction.dto.DepositRequest;
import com.tracebank.transaction.dto.TransactionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/accounts/{accountId}/deposits")
    @ApiDeposit
    public ResponseEntity<TransactionResponse> deposit(@PathVariable Long accountId,
                                                         @Valid @RequestBody DepositRequest request) {
        TransactionResponse response = transactionService.deposit(accountId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
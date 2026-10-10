package com.tracebank.transaction;

import java.util.List;
import com.tracebank.transaction.dto.DepositRequest;
import com.tracebank.transaction.dto.TransactionResponse;
import com.tracebank.transaction.dto.TransferRequest;
import com.tracebank.transaction.dto.WithdrawalRequest;

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

    @PostMapping("/accounts/{accountId}/withdrawals")
    @ApiWithdraw
    public ResponseEntity<TransactionResponse> withdraw(@PathVariable Long accountId,
                                                      @Valid @RequestBody WithdrawalRequest request) {
    TransactionResponse response = transactionService.withdraw(accountId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/accounts/{accountId}/transfers")
    @ApiTransfer
    public ResponseEntity<TransactionResponse> transfer(@PathVariable Long accountId,
                                                      @Valid @RequestBody TransferRequest request) {
    TransactionResponse response = transactionService.transfer(accountId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/accounts/{accountId}/statement")
    public ResponseEntity<List<TransactionResponse>> getStatement(@PathVariable Long accountId) {
        List<TransactionResponse> statement = transactionService.getStatement(accountId);
        return ResponseEntity.ok(statement);
}
}
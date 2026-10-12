package com.tracebank.transaction;

import com.tracebank.account.IsAccountOwner;
import com.tracebank.transaction.dto.DepositRequest;
import com.tracebank.transaction.dto.TransactionResponse;
import com.tracebank.transaction.dto.TransferRequest;
import com.tracebank.transaction.dto.WithdrawalRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/accounts/{accountId}/deposits")
    @ApiDeposit
    @IsAccountOwner
    public ResponseEntity<TransactionResponse> deposit(@PathVariable Long accountId,
                                                       @Valid @RequestBody DepositRequest request) {
        TransactionResponse response = transactionService.deposit(accountId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/accounts/{accountId}/withdrawals")
    @ApiWithdraw
    @IsAccountOwner
    public ResponseEntity<TransactionResponse> withdraw(@PathVariable Long accountId,
                                                        @Valid @RequestBody WithdrawalRequest request) {
        TransactionResponse response = transactionService.withdraw(accountId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/accounts/{accountId}/transfers")
    @ApiTransfer
    @IsAccountOwner
    public ResponseEntity<TransactionResponse> transfer(@PathVariable Long accountId,
                                                        @Valid @RequestBody TransferRequest request) {
        TransactionResponse response = transactionService.transfer(accountId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/accounts/{accountId}/statement")
    @ApiStatement
    @IsAccountOwner
    public ResponseEntity<List<TransactionResponse>> getStatement(@PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.getStatement(accountId));
    }
}
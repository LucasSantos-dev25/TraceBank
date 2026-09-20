package com.tracebank.account;

import com.tracebank.account.dto.AccountRequest;
import com.tracebank.account.dto.AccountResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/customers/{customerId}/accounts")
    public ResponseEntity<AccountResponse> create(@PathVariable Long customerId,
                                                @Valid @RequestBody AccountRequest request) {
        AccountResponse response = accountService.create(customerId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.findById(id));
    }
}
package com.tracebank.account;

import com.tracebank.account.dto.AccountRequest;
import com.tracebank.account.dto.AccountResponse;
import com.tracebank.customer.IsCustomerOwner;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/customers/{customerId}/accounts")
    @ApiCreateAccount
    @IsCustomerOwner
    public ResponseEntity<AccountResponse> create(@PathVariable Long customerId,
                                                  @Valid @RequestBody AccountRequest request) {
        AccountResponse response = accountService.create(customerId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/accounts/{accountId}")
    @ApiFindAccount
    @IsAccountOwner
    public ResponseEntity<AccountResponse> findById(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.findById(accountId));
    }
}
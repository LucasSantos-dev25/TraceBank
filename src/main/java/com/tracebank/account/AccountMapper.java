package com.tracebank.account;

import com.tracebank.account.dto.AccountResponse;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getNumber(),
                account.getType(),
                account.getBalance(),
                account.getStatus(),
                account.getOpenedAt(),
                account.getCustomer().getId()
        );
    }
}
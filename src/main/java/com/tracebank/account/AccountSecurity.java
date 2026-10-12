package com.tracebank.account;

import com.tracebank.auth.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountSecurity {

    private final AccountService accountService;
    private final CurrentUser currentUser;

    public boolean isOwner(Long accountId, Authentication authentication) {
        Long customerId = currentUser.customerId(authentication);
        return customerId != null && accountService.isOwnedBy(accountId, customerId);
    }
}
package com.tracebank.transaction;

public class AccountNotActiveException extends RuntimeException {
    public AccountNotActiveException(Long accountId) {
        super("Account is not active: " + accountId);
    }
}
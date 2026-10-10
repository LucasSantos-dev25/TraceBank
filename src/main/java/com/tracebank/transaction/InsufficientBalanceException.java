package com.tracebank.transaction;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(Long accountId) {
        super("Insufficient balance in account: " + accountId);
    }
}
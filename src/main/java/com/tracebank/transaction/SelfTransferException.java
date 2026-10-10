package com.tracebank.transaction;

public class SelfTransferException extends RuntimeException {
    public SelfTransferException(Long accountId) {
        super("Cannot transfer to the same account: " + accountId);
    }
}
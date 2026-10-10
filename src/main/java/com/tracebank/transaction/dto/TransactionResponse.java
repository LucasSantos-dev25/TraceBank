package com.tracebank.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.tracebank.transaction.TransactionType;

public record TransactionResponse(Long id,
    TransactionType type,
    BigDecimal amount,
    LocalDateTime timestamp,
    Long sourceAccountId,
    Long destinationAccountId) {

}

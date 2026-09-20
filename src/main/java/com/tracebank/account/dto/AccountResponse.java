package com.tracebank.account.dto;

import com.tracebank.account.AccountStatus;
import com.tracebank.account.AccountType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(
        Long id,
        String number,
        AccountType type,
        BigDecimal balance,
        AccountStatus status,
        LocalDateTime openedAt,
        Long customerId
) {}
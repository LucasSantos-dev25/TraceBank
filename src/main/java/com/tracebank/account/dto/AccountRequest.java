package com.tracebank.account.dto;

import com.tracebank.account.AccountType;
import jakarta.validation.constraints.NotNull;

public record AccountRequest(
        @NotNull(message = "Account type is required")
        AccountType type
) {}
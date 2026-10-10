package com.tracebank.auth.dto;

public record RegisterResponse(
        Long userId,
        Long customerId,
        String email
) {}
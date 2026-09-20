package com.tracebank.customer.dto;

import java.time.LocalDate;

public record CustomerResponse(
        Long id,
        String name,
        String cpf,
        LocalDate birthDate
) {}
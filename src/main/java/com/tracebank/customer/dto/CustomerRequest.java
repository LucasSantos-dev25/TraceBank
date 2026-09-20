package com.tracebank.customer.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CustomerRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 120, message = "Name must be at most 120 characters")
        String name,

        @NotBlank(message = "CPF is required")
        @Pattern(regexp = "\\d{11}", message = "CPF must contain 11 numeric digits")
        String cpf,

        @NotNull(message = "Birth date is required")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate
) {}
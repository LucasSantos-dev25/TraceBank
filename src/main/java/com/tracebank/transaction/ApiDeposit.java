package com.tracebank.transaction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Deposits an amount into an account", description = "Credits the informed amount to the account balance")
@ApiResponses({
        @ApiResponse(responseCode = "201", description = "Deposit completed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid data"),
        @ApiResponse(responseCode = "401", description = "Missing, invalid or expired token"),
        @ApiResponse(responseCode = "403", description = "The account does not belong to the authenticated user (or does not exist)"),
        @ApiResponse(responseCode = "422", description = "Account is not active")
})
public @interface ApiDeposit {
}
package com.tracebank.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Opens a new account", description = "Creates an active account with zero balance for the authenticated customer")
@ApiResponses({
        @ApiResponse(responseCode = "201", description = "Account created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid data"),
        @ApiResponse(responseCode = "401", description = "Missing, invalid or expired token"),
        @ApiResponse(responseCode = "403", description = "The customer does not belong to the authenticated user")
})
public @interface ApiCreateAccount {
}
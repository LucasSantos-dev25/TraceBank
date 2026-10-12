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
@Operation(summary = "Lists the account statement", description = "Returns every transaction where the account is source or destination, newest first")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Statement returned"),
        @ApiResponse(responseCode = "401", description = "Missing, invalid or expired token"),
        @ApiResponse(responseCode = "403", description = "The account does not belong to the authenticated user (or does not exist)")
})
public @interface ApiStatement {
}
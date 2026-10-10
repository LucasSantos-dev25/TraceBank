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
@Operation(summary = "Transfers an amount between two accounts", description = "Atomically debits the source account and credits the destination account")
@ApiResponses({
        @ApiResponse(responseCode = "201", description = "Transfer completed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid data or self-transfer attempt"),
        @ApiResponse(responseCode = "404", description = "Source or destination account not found"),
        @ApiResponse(responseCode = "422", description = "Account is not active or has insufficient balance")
})
public @interface ApiTransfer {
}
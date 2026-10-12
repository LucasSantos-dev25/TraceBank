package com.tracebank.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Finds a customer by id", description = "Returns the profile of the authenticated customer")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Customer found"),
        @ApiResponse(responseCode = "401", description = "Missing, invalid or expired token"),
        @ApiResponse(responseCode = "403", description = "The customer does not belong to the authenticated user")
})
public @interface ApiFindCustomer {
}
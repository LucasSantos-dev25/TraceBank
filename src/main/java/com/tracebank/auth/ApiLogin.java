package com.tracebank.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Authenticates a user", description = "Validates the credentials and returns a signed JWT access token")
@SecurityRequirements
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Authenticated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid data"),
        @ApiResponse(responseCode = "401", description = "Invalid email or password")
})
public @interface ApiLogin {
}
package com.tracebank.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {

    public Long customerId(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            return null;
        }
        Number customerId = jwt.getClaim(JwtService.CLAIM_CUSTOMER_ID);
        return customerId == null ? null : customerId.longValue();
    }
}
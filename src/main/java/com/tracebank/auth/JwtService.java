package com.tracebank.auth;

import com.tracebank.shared.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtService {

    public static final String CLAIM_EMAIL = "email";
    public static final String CLAIM_ROLE = "role";
    public static final String CLAIM_CUSTOMER_ID = "customerId";

    private final JwtEncoder jwtEncoder;
    private final JwtProperties properties;

    public String generateToken(AppUser user) {
        Instant now = Instant.now();

        JwtClaimsSet.Builder claims = JwtClaimsSet.builder()
                .issuer("tracebank-api")
                .issuedAt(now)
                .expiresAt(now.plus(properties.expiration()))
                .subject(String.valueOf(user.getId()))
                .claim(CLAIM_EMAIL, user.getEmail())
                .claim(CLAIM_ROLE, user.getRole().name());

        if (user.getCustomer() != null) {
            claims.claim(CLAIM_CUSTOMER_ID, user.getCustomer().getId());
        }

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims.build())).getTokenValue();
    }

    public long expirationSeconds() {
        return properties.expiration().toSeconds();
    }
}
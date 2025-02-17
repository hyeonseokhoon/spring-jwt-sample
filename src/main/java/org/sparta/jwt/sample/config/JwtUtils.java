package org.sparta.jwt.sample.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.sparta.jwt.sample.constants.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {
    // token prefix
    private static final String BEARER_PREFIX = "Bearer ";

    // token expired time 60 minutes
    private static final long TOKEN_TIME = 60 * 60 * 1000;

    @Value("${jwt.secret.key}")
    private String secretKey;

    private SecretKey key;

    private final SignatureAlgorithm signatureAlgorithm = Jwts.SIG.RS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // claims 에 어떤 항목을 넣느냐에 따라 args가 조정될 수 있음
    public String createToken(Long userId, UserRole role) {
        Date date = new Date();
        KeyPair pair = signatureAlgorithm.keyPair().build();

        return BEARER_PREFIX +
                Jwts.builder()
                        .subject(String.valueOf(userId))
                        .claim("userRole", role)
                        .expiration(new Date(date.getTime() + TOKEN_TIME))
                        .issuedAt(date) // 발급일
                        .signWith(pair.getPrivate(), signatureAlgorithm)
                        .compact();
    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        throw new RuntimeException("Not Found Token");
    }

    public Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }
}

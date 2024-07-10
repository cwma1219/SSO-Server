package org.example.cas_example1.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class TokenUtil {

    private static int EXPIRE;

    private static String ISS;

    private static String SUBJECT;

    private static SecretKey SECRET_KEY;

    @Value("${jwt.expire}")
    private int expire;

    @Value("${jwt.iss}")
    private String iss;

    @Value("${jwt.subject}")
    private String subject;

    @Value("${jwt.prefix}")
    private String prefix;

    @Value("${jwt.key}")
    private String jwtKey;

    @PostConstruct
    public void init() {
        ISS = iss;
        SUBJECT = subject;
        EXPIRE = expire;
        SECRET_KEY = new SecretKeySpec(jwtKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    }

    public static String genToken(String login) {
        Date exprierDate = Date.from(Instant.now().plusSeconds(EXPIRE));
        return Jwts.builder()
                .header()
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
                .claim("login", login)
                .expiration(exprierDate)
                .issuedAt(new Date())
                .subject(SUBJECT)
                .issuer(ISS)
                .signWith(SECRET_KEY, Jwts.SIG.HS256)
                .compact();
    }

    public static Jws<Claims> parseClaim(String token) {
        token = getJwt(token);
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token);
    }

    public static Claims parsePayload(String token) {
        return parseClaim(token).getPayload();
    }

    private static String getJwt(String token) {
        if (token == null) {
            throw new RuntimeException("請重新登入");
        }
        return token;
    }
}

package com.example.messx.redit.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.*;
import java.time.Instant;
import java.util.Date;


@Service
public class JwtProvider {
    private static final String SECRET_KEY = "TEST_KEY";
    //The JWT signature algorithm we will be using to sign the token
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationMillis;

    public String generateToken(Authentication authentication){
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        User principal = (User) authentication.getPrincipal();
        String compact = Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(now)
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationMillis)))
                .signWith(signatureAlgorithm, signingKey)
                .compact();
        return compact;
    }

    public String generateTokenByUsername(String username){
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        String compact = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationMillis)))
                .signWith(signatureAlgorithm, signingKey)
                .compact();
        return compact;
    }

    public Jws<Claims> validateAndGetClaimObj(String jwt){
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt);
        return claims;
    }

    public Claims getClaim(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = validateAndGetClaimObj(jwt).getBody();
        return claims;
    }

    public String getUserNameFromToken(String jwt){
        Claims claims = getClaim(jwt);
        return claims.getSubject();
    }

    public Long getJwtExpirationMillis(){
        return jwtExpirationMillis;
    }

}

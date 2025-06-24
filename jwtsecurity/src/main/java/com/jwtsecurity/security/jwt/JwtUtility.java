package com.jwtsecurity.security.jwt;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtility {

    @Value("${jwt.publickey}")
    private String publicKeyString;

    @Value("${jwt.privatekey}")
    private String privateKeyString;

    @Value("${jwt.expiration-milliseconds}")
    private long jwtExpirationMs;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void init() throws Exception {
        byte[] decodedPrivate = Base64.getDecoder().decode(privateKeyString);
        byte[] decodedPublic = Base64.getDecoder().decode(publicKeyString);

        /// Use Specific encoders for private and public keys///
        // Convert Base64 to PrivateKey
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decodedPrivate);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.privateKey = keyFactory.generatePrivate(privateKeySpec);

        // Convert Base64 to PublicKey
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decodedPublic);
        this.publicKey = keyFactory.generatePublic(publicKeySpec);
    }

    public String generateToken(Authentication auth) {
        return Jwts.builder()
                .subject(auth.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}

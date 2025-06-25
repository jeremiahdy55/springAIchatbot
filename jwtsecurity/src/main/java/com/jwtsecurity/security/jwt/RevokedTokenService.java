package com.jwtsecurity.security.jwt;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class RevokedTokenService {

    private final Set<String> revokedTokens = ConcurrentHashMap.newKeySet();

    public void revokeToken(String jti) {
        revokedTokens.add(jti);
    }

    public boolean isRevoked(String jti) {
        return revokedTokens.contains(jti);
    }
}

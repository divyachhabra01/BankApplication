package com.springboot.authentication.BankApplication.util;

import com.springboot.authentication.BankApplication.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Component
public class JwtUtil {
    private static final String ISSUER="Bank";
    private static final String SUBJECT = "JWT Token";

    public String generateToken(Authentication authentication) {
        String jwt = null;
        if (null != authentication) {
            jwt = Jwts.builder().issuer(ISSUER).subject(SUBJECT)
                    .claim("username", authentication.getName())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .issuedAt(new Date())
                    .expiration(new Date((new Date()).getTime() + 30000000))
                    .signWith(getSignKey()).compact();
        }
        return jwt;
    }

    private Key getSignKey() {
        byte[] bytes = Decoders.BASE64.decode(SecurityConstants.JWT_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}
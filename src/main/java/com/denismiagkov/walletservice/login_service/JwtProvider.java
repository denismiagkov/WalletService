package com.denismiagkov.walletservice.login_service;

import com.denismiagkov.walletservice.PropertyFile;
import com.denismiagkov.walletservice.application.dto.PlayerDto;
import com.denismiagkov.walletservice.application.service.serviceImpl.Entry;
import com.denismiagkov.walletservice.domain.model.Player;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtProvider {

    private final SecretKey JWT_ACCESS_SECRET_KEY;
    private final SecretKey JWT_REFRESH_SECRET_KEY;

    public JwtProvider() {
        String valueOfJwtAccessSecretKey = PropertyFile.getProperties("JWT_ACCESS_SECRET_KEY");
        String valueOfJwtRefreshSecretKey = PropertyFile.getProperties("JWT_REFRESH_SECRET_KEY");
        this.JWT_ACCESS_SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(valueOfJwtAccessSecretKey));
        this.JWT_REFRESH_SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(valueOfJwtRefreshSecretKey));
    }

    public SecretKey getJWT_ACCESS_SECRET_KEY() {
        return JWT_ACCESS_SECRET_KEY;
    }

    public String generateAccessToken(Entry entry) {
        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationInstant = now.plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(entry.getLogin())
                .setExpiration(accessExpiration)
                .signWith(JWT_ACCESS_SECRET_KEY)
                .compact();
    }

    public String generateRefreshToken(Entry entry) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(entry.getLogin())
                .setExpiration(refreshExpiration)
                .signWith(JWT_REFRESH_SECRET_KEY)
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, JWT_ACCESS_SECRET_KEY);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, JWT_REFRESH_SECRET_KEY);
    }

    private boolean validateToken(String token, Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            System.out.println(expEx.getMessage());
            // log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            System.out.println(unsEx.getMessage());
            // log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            System.out.println(mjEx.getMessage());
            //  log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            System.out.println(sEx.getMessage());
//         //   log.error("Invalid signature", sEx);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //   log.error("invalid token", e);
        }
        return false;
    }

    public Claims getAccessClaims(String token) {
        return getClaims(token, JWT_ACCESS_SECRET_KEY);
    }

    public Claims getRefreshClaims(String token) {
        return getClaims(token, JWT_REFRESH_SECRET_KEY);
    }

    private Claims getClaims(String token, Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}

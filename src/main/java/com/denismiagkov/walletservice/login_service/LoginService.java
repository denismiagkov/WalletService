package com.denismiagkov.walletservice.login_service;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//
//import java.sql.Timestamp;
//import java.util.UUID;

public class LoginService {




















    /*private final Algorithm algorithm = Algorithm.HMAC256("wallet");
    private final JWTVerifier verifier = JWT.require(algorithm).withIssuer("wallet").build();


    String getJwtToken(int userId) {
        String jwtToken = JWT.create()
                .withIssuer("wallet")
                .withSubject("wallet_login")
                .withClaim("userId", userId)
                .withIssuedAt(new Timestamp(System.currentTimeMillis()))
                .withExpiresAt(new Timestamp(System.currentTimeMillis()+ 300_000L))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Timestamp(System.currentTimeMillis() + 1000L))
                .sign(algorithm);


    }*/

}

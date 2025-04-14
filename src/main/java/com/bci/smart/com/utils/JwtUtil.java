package com.bci.smart.com.utils;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

  private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Clave secreta interna

  public static String generateToken(String userId, String email) {
    long nowMillis = System.currentTimeMillis();
    long expMillis = nowMillis + 3600000;

    return Jwts.builder()
        .setSubject(userId)
        .setIssuedAt(new Date(nowMillis))
        .setExpiration(new Date(expMillis))
        .claim("email", email)
        .signWith(key)
        .compact();
  }
}

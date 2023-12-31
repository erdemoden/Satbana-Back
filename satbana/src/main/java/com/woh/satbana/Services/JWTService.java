package com.woh.satbana.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.UUID;

@Service
public class JWTService {
    @Value("${satbana.secret-hash}")
    private String HASH;
    @Value("${satbana.expiresin}")
    private Long EXPIRES_IN;
    public String generateJwtToken(UUID id) throws NoSuchAlgorithmException, NoSuchProviderException {
        return Jwts.builder()
                .subject(id.toString())
                .issuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+EXPIRES_IN))
                .signWith(SignatureAlgorithm.HS256,HASH)
                .compact();
    }
   public String getUserNameFromJwt(String jwtToken){
        return Jwts.parser()
                .setSigningKey(HASH)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject()
                .toString();
    }
    public boolean isJwtValid(String jwtToken){
        try {
            Claims claims =  Jwts.parser().setSigningKey(HASH).build().parseClaimsJws(jwtToken).getBody();
            return !isJwtExpired(claims);
        }
        catch (Exception e) {
            return false;
        }
    }
    public boolean isJwtExpired(Claims claims){
       return claims.getExpiration().before(new Date(System.currentTimeMillis()));
    }
}

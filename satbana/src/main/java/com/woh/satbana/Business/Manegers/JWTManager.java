package com.woh.satbana.Business.Manegers;

import com.woh.satbana.Business.Services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class JWTManager implements JWTService {
    @Value("${satbana.secret-hash}")
    private String HASH;
    @Value("${satbana.database-hash}")
    private String HASH_FOR_DATABASE;
    @Value("${satbana.expiresin}")
    private Long EXPIRES_IN;
    private String randomStringBase = "ABCDEFGHIJKLMNOPRSTUVYZQWabcdefghijklmnoprstuvyzqw!'^+%&/()=?_1234567890£>#$½§{[]}|";
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


    public String generateJwtTokenForDatabase() {
        return Jwts.builder()
                .subject(randomStringGenerator())
                .issuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+EXPIRES_IN))
                .signWith(SignatureAlgorithm.HS256,HASH_FOR_DATABASE)
                .compact();
    }

    @Override
    public String randomStringGenerator() {
        StringBuilder stringBuilder = new StringBuilder(54);
        Random random = new Random();
        for (int i = 0;i<54;i++){
            stringBuilder.append(randomStringBase.charAt(random.nextInt(randomStringBase.length())));
        }
        return stringBuilder.toString();
    }
}

package com.woh.satbana.Business.Manegers;

import com.woh.satbana.Business.Services.JWTService;
import com.woh.satbana.Redis.RedisStore;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JWTManager implements JWTService {
    @Value("${satbana.expiresin}")
    private Long EXPIRES_IN;
    @Value("${satbana.redishash}")
    String REDIS_HASH;

    private final RedisStore redisStore;

    public String generateJwtToken(UUID id) throws NoSuchAlgorithmException, NoSuchProviderException {
        return Jwts.builder()
                .subject(id.toString())
                .issuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+EXPIRES_IN))
                .signWith(SignatureAlgorithm.HS256,(String) redisStore.getValue(REDIS_HASH))
                .compact();
    }

   public String getUserIdFromJwt(String jwtToken){
        return Jwts.parser()
                .setSigningKey((String) redisStore.getValue(REDIS_HASH))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject()
                .toString();
    }

    public boolean isJwtValid(String jwtToken){
        try {
            Claims claims =  Jwts.parser().setSigningKey((String) redisStore.getValue(REDIS_HASH)).build().parseClaimsJws(jwtToken).getBody();
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

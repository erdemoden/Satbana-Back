package com.woh.satbanagateway.Services;
import com.woh.satbanagateway.Redis.RedisStore;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JWTService {
    @Value("${satbana.secret-hash}")
    private String HASH;
    @Value("${satbana.expiresin}")
    private Long EXPIRES_IN;
    @Value("${satbana.randomStringBase}")
    private String randomStringBase;
    @Value("${satbana.redishash}")
    String redisHash;

    private final RedisStore redisStore;

    @PostConstruct
    public void init(){
        if(redisStore.isExists(redisHash)){
            redisStore.delete(redisHash);
        }
        String hash = randomStringGenerator();
        redisStore.put(redisHash,hash,2592000);
    }

    public String generateJwtToken(UUID id) throws NoSuchAlgorithmException, NoSuchProviderException {
        return Jwts.builder()
                .subject(id.toString())
                .issuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+EXPIRES_IN))
                .signWith(SignatureAlgorithm.HS256, (String) redisStore.getValue(redisHash))
                .compact();
    }
   public String getIdFromJwt(String jwtToken){
       try {
           return Jwts.parser()
                   .setSigningKey((String) redisStore.getValue(redisHash))
                   .build()
                   .parseClaimsJws(jwtToken)
                   .getBody()
                   .getSubject()
                   .toString();
       } catch (Exception e) {
           return null;
       }
    }
    public boolean isJwtValid(String jwtToken){
        try {
            Claims claims =  Jwts.parser().setSigningKey((String) redisStore.getValue(redisHash)).build().parseClaimsJws(jwtToken).getBody();
            return !isJwtExpired(claims);
        }
        catch (Exception e) {
            return false;
        }
    }
    public boolean isJwtExpired(Claims claims){
       return claims.getExpiration().before(new Date(System.currentTimeMillis()));
    }

    public String randomStringGenerator() {
        StringBuilder stringBuilder = new StringBuilder(64);
        Random random = new Random();
        for (int i = 0;i<64;i++){
            stringBuilder.append(randomStringBase.charAt(random.nextInt(randomStringBase.length())));
        }
        return stringBuilder.toString();
    }
}

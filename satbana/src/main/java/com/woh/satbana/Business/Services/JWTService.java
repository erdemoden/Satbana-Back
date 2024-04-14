package com.woh.satbana.Business.Services;

import com.woh.satbana.Entities.User;
import com.woh.satbana.Redis.RedisStore;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.UUID;

public interface JWTService {
    String generateJwtToken(UUID id) throws NoSuchAlgorithmException, NoSuchProviderException;
    String getUserIdFromJwt(String jwtToken);
    boolean isJwtValid(String jwtToken);
    boolean isJwtExpired(Claims claims);


}

package com.woh.satbana.Business.Services;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.UUID;

public interface JWTService {
    String generateJwtToken(UUID id) throws NoSuchAlgorithmException, NoSuchProviderException;
    String getUserNameFromJwt(String jwtToken);
    boolean isJwtValid(String jwtToken);
    boolean isJwtExpired(Claims claims);

    String generateJwtTokenForDatabase();

    String randomStringGenerator();

}

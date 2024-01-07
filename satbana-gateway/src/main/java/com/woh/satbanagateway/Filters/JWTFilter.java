package com.woh.satbanagateway.Filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woh.satbanagateway.Responses.JWTFilterResponse;
import com.woh.satbanagateway.Responses.AuthResponse;
import com.woh.satbanagateway.Services.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.awt.image.DataBuffer;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTFilter implements GlobalFilter, Ordered {

    @Value("${jwtfilter.exclude.paths}")
    String safePaths;
    private final ObjectMapper mapper = new ObjectMapper();
    private final JWTService jwtService;
    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        JWTFilterResponse jwtFilterResponse = new JWTFilterResponse();
        log.info("custom global filter");

        try {
            String extractedJwt = extractJwtFromRequest(exchange.getRequest());

            for (String path : safePaths.split(";")){
                log.info(exchange.getRequest().getURI().getPath());
                if(exchange.getRequest().getURI().getPath().startsWith(path)){
                  return  chain.filter(exchange);
                }
            }
            if(extractedJwt !=null && StringUtils.hasText(extractedJwt) && jwtService.isJwtValid(extractedJwt)){
               return chain.filter(exchange);
            }
            else{
                jwtFilterResponse.setAuthenticated(false);
            }
            return exchange.getResponse().writeWith(
                    Mono.just(exchange.getResponse().bufferFactory().wrap(
                            new ObjectMapper().writeValueAsBytes(jwtFilterResponse)
                    ))
            );
        } catch (JsonProcessingException e) {
            jwtFilterResponse.setAuthenticated(false);
            return exchange.getResponse().writeWith(
                    Mono.just(exchange.getResponse().bufferFactory().wrap(
                            new ObjectMapper().writeValueAsBytes(jwtFilterResponse)
                    ))
            );
        }
    }

    private String extractJwtFromRequest(ServerHttpRequest request) {
        String bearer = request.getHeaders().getFirst("Authorization");
        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer:")) {
            return bearer.substring("Bearer:".length()+1);
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}

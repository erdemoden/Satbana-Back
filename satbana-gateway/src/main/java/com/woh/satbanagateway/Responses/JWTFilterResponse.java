package com.woh.satbanagateway.Responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;
@Data
public class JWTFilterResponse{

    private boolean authenticated;

}

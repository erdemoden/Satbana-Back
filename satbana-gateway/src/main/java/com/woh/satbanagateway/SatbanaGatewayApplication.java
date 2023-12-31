package com.woh.satbanagateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@SpringBootApplication
@Slf4j
public class SatbanaGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SatbanaGatewayApplication.class, args);
	}

	@Bean
	@Order(-1)
	public GlobalFilter a() {
		return (exchange, chain) -> {
			log.info("first pre filter");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.info("third post filter");
			}));
		};
	}

}

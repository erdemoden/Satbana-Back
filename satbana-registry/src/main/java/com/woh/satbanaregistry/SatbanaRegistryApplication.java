package com.woh.satbanaregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SatbanaRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SatbanaRegistryApplication.class, args);
	}

}

package com.app.businessBridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BusinessBridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessBridgeApplication.class, args);
	}

}

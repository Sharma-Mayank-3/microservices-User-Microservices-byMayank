package com.user.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserMicroserviceByMayankApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMicroserviceByMayankApplication.class, args);
	}

}

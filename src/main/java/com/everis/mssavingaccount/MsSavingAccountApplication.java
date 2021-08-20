package com.everis.mssavingaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsSavingAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsSavingAccountApplication.class, args);
	}

}

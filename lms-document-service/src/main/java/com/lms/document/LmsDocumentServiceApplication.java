package com.lms.document;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LmsDocumentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsDocumentServiceApplication.class, args);
	}

}

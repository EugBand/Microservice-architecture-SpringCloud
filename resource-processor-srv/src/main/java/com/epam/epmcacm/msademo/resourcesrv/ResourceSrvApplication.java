package com.epam.epmcacm.msademo.resourcesrv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ResourceSrvApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceSrvApplication.class, args);
	}

}

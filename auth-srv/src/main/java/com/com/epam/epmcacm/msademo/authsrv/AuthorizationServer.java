package com.com.epam.epmcacm.msademo.authsrv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthorizationServer {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServer.class, args);
    }
}

package com.mcommande;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication

//kai9era la configuration li externe =>  pour Spring Cloud Config
@EnableConfigurationProperties

// pour Eureka Client
@EnableDiscoveryClient
public class McommandeApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(McommandeApplication.class, args);
    }
}

package com.transaction.microservice;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.transaction.microservice", "com.codes.common"})
@EnableJpaRepositories(basePackages = "com.transaction.microservice.repository")
@EntityScan("com.codes.common.entity")
public class TransactionApp {
    public static void main(String[] args) {
        SpringApplication.run(TransactionApp.class, args);
    }
}

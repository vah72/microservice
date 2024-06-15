package com.example.account;

import com.example.account.model.Role;
import com.example.account.repository.RoleRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableCaching
@EnableFeignClients
@RefreshScope
@ComponentScans({@ComponentScan("com.example.account.controller"), @ComponentScan("com.example.account.config"), @ComponentScan("com.example.account.service"), @ComponentScan("com.example.account.jwt")})
@EnableMongoRepositories("com.example.account.repository")
@EntityScan("com.example.account.model")
@OpenAPIDefinition(info = @Info(title="Account Application", version="3.0"))
public class AccountApplication {



    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }


    //clear cache
//    @CacheEvict(allEntries = true, value = {"account"})
//    @Scheduled(fixedDelay =  5000, initialDelay = 5000 )
//    public void clearCache(){
//        System.out.println("Clearing Cache");
//    }
}
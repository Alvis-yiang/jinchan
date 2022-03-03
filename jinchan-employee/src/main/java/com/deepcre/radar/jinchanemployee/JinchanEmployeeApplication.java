package com.deepcre.radar.jinchanemployee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@MapperScan("com.deepcre.radar.jinchanemployee.dao")
@EnableDiscoveryClient
public class JinchanEmployeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JinchanEmployeeApplication.class, args);
    }

}

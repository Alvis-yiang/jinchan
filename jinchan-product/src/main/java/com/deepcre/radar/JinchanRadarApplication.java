package com.deepcre.radar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 1、整合MyBatis-Plus
 *  1）导入依赖
 *  2）配置
 *      1、配置数据源
 *          1）、导入数据库驱动
 *          2）、在application.yml内配置数据源相关信息
 *      2、配置MyBatis-Plus
 *          1)、使用@MapperScan
 *          2)、告诉MyBatis-Plus映射文件位置
 *
 *
 * 2、想要远程调用别的服务
 *  1）、引入open-feign
 *  2）、编写一个接口，告诉springCloud这个接口需要调用远程服务
 *      1。声明接口的每一个方法都是调用哪个远程服务的那个请求
 *  3）、开启远程调用功能@EnableFeignClients(basePackages = "com.deepcre.jinchan.radar.feign")
 *
 *
 * 3、逻辑删除
 *  1)配置全局逻辑删除规则（省略）
 *  2)配置逻辑删除的组件Bean（省略）
 *  3)加上逻辑删除注解@TableLogic
 */
@EnableTransactionManagement
@MapperScan("com.deepcre.radar.dao")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.deepcre.radar.feign")
public class JinchanRadarApplication {

    public static void main(String[] args) {
        SpringApplication.run(JinchanRadarApplication.class, args);
    }

}

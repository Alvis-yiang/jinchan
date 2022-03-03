package com.deepcremall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @author alvis-yiang
 * @create 2022-01-07 4:30 PM
 */
@Configuration
public class JinchanCorsConfiguration {

    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //1.配置跨域
        corsConfiguration.addAllowedHeader("*");//允许请求头
        corsConfiguration.addAllowedMethod("*");//允许请求方法
        corsConfiguration.addAllowedOrigin("*");//允许请求来源
        corsConfiguration.setAllowCredentials(true);//是否允许携带cookie

        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsWebFilter(source);
    }


}

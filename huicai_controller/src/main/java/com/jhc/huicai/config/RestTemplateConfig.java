package com.jhc.huicai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <h3>tech_backend</h3>
 * <p>后端发送请求</p>
 *
 * @author : zhengyue
 * @date : 2020-07-21 16:36
 **/
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
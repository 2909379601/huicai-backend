package com.jhc.huicai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: Erruihhh
 * @Date: 2022/5/16
 * @Time: 15:02
 * @PROJECT_NAME: huicai-backend
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.jhc.huicai.mapper")
@EnableSwagger2
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
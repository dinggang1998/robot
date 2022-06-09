package com.learn.robot;

//这里的MapperScan不可以用org
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.learn.robot")
@EnableAsync
@EnableScheduling
@MapperScan(basePackages = {
        "com.learn.robot.mapper",
        "com.learn.robot.dao"
})
@EnableDiscoveryClient
public class RobotApplication {

    public static void main(String[] args) {
        SpringApplication.run(RobotApplication.class, args);
    }

}

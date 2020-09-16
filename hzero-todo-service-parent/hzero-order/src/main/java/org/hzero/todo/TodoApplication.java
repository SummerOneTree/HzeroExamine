package org.hzero.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

/**
 * @EnableChoerodonResourceServer 用于开启资源认证、关闭 Security 安全认证
 * @EnableDiscoveryClient // 启用注册中心客户端
 * @Date 2020/8/14 10:08
 * @Author Summer_OneTree
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableDiscoveryClient
public class TodoApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }
}

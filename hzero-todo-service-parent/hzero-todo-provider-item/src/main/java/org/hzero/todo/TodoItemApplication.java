package org.hzero.todo;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @EnableChoerodonResourceServer 用于开启资源认证、关闭 Security 安全认证
 * @EnableDiscoveryClient // 启用注册中心客户端
 * @Date 2020/8/14 10:08
 * @Author Summer_OneTree
 */
@SpringBootApplication
@RestController
@EnableAsync
public class TodoItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoItemApplication.class, args);
    }
}

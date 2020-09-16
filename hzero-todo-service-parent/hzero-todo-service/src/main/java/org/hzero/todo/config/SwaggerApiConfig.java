package org.hzero.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.Tag;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Date 2020/8/14 14:57
 * @Author Summer_OneTree
 */
@Configuration
public class SwaggerApiConfig {
    public static final String USER = "User";
    public static final String TASK = "Task";
    public static final String ITEM = "Item";


    @Autowired
    public SwaggerApiConfig(Docket docket) {
        docket.tags(
                new Tag(USER, "用户信息"),
                new Tag(TASK, "任务信息"),
                new Tag(ITEM, "物料信息")
        );
    }
}

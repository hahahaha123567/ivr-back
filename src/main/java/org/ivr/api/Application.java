package org.ivr.api;

import org.ivr.api.config.BootFailEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zhangyaoxin@yiwise.com
 * @description boot application
 * @create 2019/04/02
 **/
@SpringBootApplication
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder(Application.class).build();
        application.addListeners(new BootFailEvent());
        application.run(args);
        logger.info("boot success");
    }

}

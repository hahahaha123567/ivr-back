package org.ivr.api.config;

import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;

import java.util.Arrays;

/**
 * @author zhangyaoxin@yiwise.com
 * @description boot failed event
 * @create 2019/04/02
 **/
public class BootFailEvent implements ApplicationListener<ApplicationFailedEvent> {

    @Override
    public void onApplicationEvent(ApplicationFailedEvent applicationFailedEvent) {
        Throwable throwable = applicationFailedEvent.getException();
        System.err.println("boot failed");
        System.err.println(Arrays.toString(throwable.getStackTrace()));
    }
}

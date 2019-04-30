package org.ivr.api.helper;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/10
 **/
@Component
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> requiredType) {
        return context.getBean(beanName, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) {
        String name = requiredType.getName();

        T obj;
        try {
            obj = getBean(name, requiredType);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }

        return obj;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
        ApplicationContextHelper.context = context;
    }

}

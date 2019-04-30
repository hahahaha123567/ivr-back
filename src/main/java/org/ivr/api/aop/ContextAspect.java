package org.ivr.api.aop;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.ivr.api.dal.recognition.ServiceEnum;
import org.ivr.api.exception.UnrecognizedException;
import org.ivr.api.service.context.ContextService;
import org.ivr.api.service.recognition.RecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author zhangyaoxin@yiwise.com
 * @description TODO
 * @create 2019/04/15
 **/
@Aspect
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContextAspect {

    ContextService contextService;
    RecognitionService recognitionService;

    @Autowired
    public ContextAspect(ContextService contextService, RecognitionService recognitionService) {
        this.contextService = contextService;
        this.recognitionService = recognitionService;
    }

    /**
     * 识别参数后更新上下文
     */
    @Around("@annotation(contextAnnotation)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint, ContextAnnotation contextAnnotation) {
        String property = contextAnnotation.property();
        Optional retOption = Optional.empty();
        try {
            retOption = (Optional) proceedingJoinPoint.proceed();
        } catch (IllegalArgumentException ignore) {

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        if (retOption.isPresent()) {
            // update context
            contextService.set(property, retOption.get());
        } else {
            // read context
            retOption = Optional.ofNullable(contextService.get(property));
        }
        return retOption;
    }

    /**
     * 若识别服务失败, 且上次识别成功, 且上次执行失败, 更新识别结果
     */
    @Around("execution(public * org.ivr.api.service.recognition.RecognitionService.service())")
    public Object doRecognizeAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Optional serviceEnum = (Optional) proceedingJoinPoint.proceed();
        if (!serviceEnum.isPresent() && !ServiceEnum.DEFAULT.equals(contextService.get(ContextService.SERVICE_KEY)) && !(Boolean) contextService.get(ContextService.SERVICE_RESULT_KEY)) {
            serviceEnum = contextService.get(ContextService.SERVICE_KEY);
        }
        return serviceEnum;
    }

    /**
     * 识别服务后更新结果
     */
    @AfterReturning(value = "execution(public * org.ivr.api.service.recognition.RecognitionService.service())", returning = "ret")
    public void doRecognizeAfterReturning(Object ret) {
        Optional retOption = (Optional) ret;
        if (retOption.isPresent()) {
            contextService.set(ContextService.SERVICE_KEY, ret);
        }
    }

    /**
     * 执行服务成功后更新结果
     */
    @AfterReturning("execution(public * org.ivr.api.service.menu.MenuService.navigate())")
    public void doExecuteAfterReturning() {
        contextService.set(ContextService.SERVICE_RESULT_KEY, Boolean.TRUE);
    }

    /**
     * 执行服务失败后更新结果
     */
    @AfterThrowing(value = "execution(public * org.ivr.api.service.menu.MenuService.navigate())", throwing = "ex")
    public void doExecuteAfterThrowing(Exception ex) {
        if (!(ex instanceof UnrecognizedException)) {
            contextService.set(ContextService.SERVICE_RESULT_KEY, Boolean.FALSE);
        }
    }
}

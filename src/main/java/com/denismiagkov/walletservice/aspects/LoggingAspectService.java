package com.denismiagkov.walletservice.aspects;

import com.denismiagkov.loggingstarter.aspects.LoggingAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Conditional(EnableLoggingCondition.class)
public class LoggingAspectService {

    private final LoggingAspect loggingAspect;


    @Autowired
    public LoggingAspectService(LoggingAspect loggingAspect) {
        this.loggingAspect = loggingAspect;
    }

    @Pointcut("execution(* com.denismiagkov.walletservice.infrastructure.controller.Controller.*(..))")
    private void controller_logging() {
    }

    @Around("controller_logging()")
    private Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return this.loggingAspect.logging(proceedingJoinPoint);
    }

}

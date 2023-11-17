package com.denismiagkov.walletservice.aspects;

import com.denismiagkov.loggingstarter.aspects.LoggingAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
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

    @Pointcut("execution(" +
            "* com.denismiagkov.walletservice.infrastructure.in.exception_hahdling.handlers.GlobalExceptionHandler.*(..))")
    private void exception_handler_logging() {
    }

    @Around("controller_logging()")
    private Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return this.loggingAspect.logging(proceedingJoinPoint);
    }

    @After("exception_handler_logging()")
    private void loggingException(JoinPoint joinPoint) throws Throwable {
        this.loggingAspect.loggingException(joinPoint);
    }
}

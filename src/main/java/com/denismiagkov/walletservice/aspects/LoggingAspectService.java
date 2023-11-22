package com.denismiagkov.walletservice.aspects;

import com.denismiagkov.loggingstarter.aspects.LoggingAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
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

    @Pointcut("execution(" +
            "* com.denismiagkov.walletservice.infrastructure.login_service.AuthService.*(..))")
    private void filter_logging() {
    }

    @Around("controller_logging()")
    private Object logMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return this.loggingAspect.logMethod(proceedingJoinPoint);
    }

    @After("exception_handler_logging()")
    private void logExceptionHandling(JoinPoint joinPoint) {
        this.loggingAspect.logExceptionHandling(joinPoint);
    }

    @AfterThrowing(pointcut = "filter_logging()", throwing = "e")
    private void logExceptionThrowing(Exception e) throws Throwable {
        this.loggingAspect.logExceptionThrowing(e);
    }
}

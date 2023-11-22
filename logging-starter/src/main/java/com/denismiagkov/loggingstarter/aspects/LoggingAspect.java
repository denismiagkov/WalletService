package com.denismiagkov.loggingstarter.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Класс реализует бизнес-логику замера и логирования времени выполнения методов
 */
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Метод реализует замер и логирование времени выполнения метода
     */
    public Object logMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("Calling method " + proceedingJoinPoint.getSignature());
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        logger.info("Execution of method " + proceedingJoinPoint.getSignature() +
                " finished. Execution time is " + (endTime - startTime) + " ms");
        return result;
    }

    /**
     * Метод реализует логирование обработки исключений ExceptionHandler
     */
    public void logExceptionHandling(JoinPoint joinPoint) {
        logger.error("Execution error: ", joinPoint.getArgs());
    }

    /**
     * Метод реализует логирование выбрасываемых исключений
     */
    public void logExceptionThrowing(Exception e) {
        logger.error("Execution error: " + e.getClass() + ": " + e.getMessage());
    }
}

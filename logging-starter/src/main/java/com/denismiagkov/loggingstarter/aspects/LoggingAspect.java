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
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("Calling method " + proceedingJoinPoint.getSignature());
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        logger.info("Execution of method " + proceedingJoinPoint.getSignature() +
                " finished. Execution time is " + (endTime - startTime) + " ms");
        return result;
    }

    /**
     * Метод реализует логирование ошибок при выполнении методов
     */
    public void loggingException(JoinPoint joinPoint) throws Throwable {
        logger.error("Execution error: ", joinPoint.getArgs());
    }
}

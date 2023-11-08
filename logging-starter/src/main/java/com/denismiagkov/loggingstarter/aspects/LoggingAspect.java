package com.denismiagkov.loggingstarter.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

/**
 * Класс реализует бизнес-логику замера и логирования времени выполнения методов
 * */
@Component
public class LoggingAspect {

    /**
     * Метод реализует замер и логирование времени выполнения метода
     * */
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("*****************************************************************************************");
        System.out.println("Calling method " + proceedingJoinPoint.getSignature());
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        System.out.println("Execution of method " + proceedingJoinPoint.getSignature() +
                " finished. Execution time is " + (endTime - startTime) + " ms");
        System.out.println("*****************************************************************************************");
        return result;
    }
}

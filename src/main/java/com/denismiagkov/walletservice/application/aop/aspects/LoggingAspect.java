package com.denismiagkov.walletservice.application.aop.aspects;

import com.denismiagkov.walletservice.application.service.serviceImpl.OperationServiceImpl;
import com.denismiagkov.walletservice.application.service.serviceImpl.PlayerServiceImpl;
import com.denismiagkov.walletservice.domain.model.OperationStatus;
import com.denismiagkov.walletservice.domain.model.OperationType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.sql.Timestamp;

@Aspect
public class LoggingAspect {

    @Pointcut("within(@com.denismiagkov.walletservice.application.aop.annotations.Loggable *) && execution(* *(..))")
    public void annotatedByLoggable() {
    }

    @Around("annotatedByLoggable()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Calling method " + proceedingJoinPoint.getSignature());
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        System.out.println("Execution of method " + proceedingJoinPoint.getSignature() +
                " finished. Execution time is " + (endTime - startTime) + " ms");
        return result;
    }

//    OperationServiceImpl operationService;
//    PlayerServiceImpl playerService;
//
//    public LoggingAspect(OperationServiceImpl operationService, PlayerServiceImpl playerService) {
//        this.operationService = operationService;
//        this.playerService = playerService;
//    }
//
//    public void afterRegistrationPlayer() {
//
//    }

    @AfterReturning("execution(public boolean " +
            "com.denismiagkov.walletservice.application.service.Service.authorizePlayer(String, String) " +
            "throws RuntimeException)")
    public void afterAuthorizePlayer() {
//        operationService.putOnLog(playerService.getPlayerId("login1"), OperationType.AUTHORIZATION,
//                new Timestamp(System.currentTimeMillis()), OperationStatus.SUCCESS);
//        System.out.println(operationService.getLog());
        System.out.println("HELLO, THIS IS ASPECT");
    }

    @Before("execution(public AccountDto getCurrentBalance(String))")
    public void beforeGetCurrentBalance() {
//        operationService.putOnLog(playerService.getPlayerId("login1"),
//                OperationType.BALANCE_LOOKUP, new Timestamp(System.currentTimeMillis()),
//                OperationStatus.SUCCESS);
        System.out.println("HELLO, THIS IS ASPECT");
    }

    public void afterGetTransactionHistory() {

    }

    public void afterTopUpAccount() {

    }

    public void afterWriteOffFunds() {

    }

    public void afterExitFromApp() {

    }
}



package com.example.springbootaop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class LoggingAspect {

    @Before("execution(* com.example.springbootaop.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        String args = Arrays.toString(joinPoint.getArgs());
        log.info("Executing method: " + methodName + " with arguments: " + args);
    }

    @After("execution(* com.example.springbootaop.service.*.*(..))")
    public void after(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("Method executed: " + methodName);
    }

    /*@AfterReturning("execution(* com.example.springbootaop.service.EmployeeService.saveEmployee(..)) || execution(* com.example.springbootaop.service.EmployeeService.updateEmployee(..))")
    public void after(JoinPoint joinPoint, LocalDate joinedOn) {
        if (joinedOn.equals(LocalDate.now())) {
            log.info("HAPPY BIRTHDAY!");
        }
    }*/

}

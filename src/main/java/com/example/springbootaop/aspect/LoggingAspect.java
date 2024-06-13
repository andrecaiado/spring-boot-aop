package com.example.springbootaop.aspect;

import com.example.springbootaop.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
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

    @AfterReturning(pointcut = "execution(* com.example.springbootaop.service.EmployeeService.saveEmployee(..)) || execution(* com.example.springbootaop.service.EmployeeService.updateEmployee(..))", returning = "employee")
    public void afterReturningSaveEmployee(JoinPoint joinPoint, Employee employee) {
        LocalDate today = LocalDate.now();
        if (employee.getJoinedOn().getMonth() == today.getMonth() && employee.getJoinedOn().getDayOfMonth() == today.getDayOfMonth()) {
            log.info("HAPPY COMPANY ANNIVERSARY " + employee.getFirstName() + " " + employee.getLastName() + "!!!");
        }
    }


    @AfterThrowing(pointcut = "execution(* com.example.springbootaop.service.EmployeeService.saveEmployee(..)) || execution(* com.example.springbootaop.service.EmployeeService.updateEmployee(..))", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().toShortString();
        log.error("Exception thrown by " + methodName + ": " + exception.getMessage());
    }

}

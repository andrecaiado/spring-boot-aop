# Spring Boot AOP project

This is a Spring Boot application that demonstrates how to use Aspect-Oriented Programming (AOP).

## Contents
- [Dependency](#dependency)
- [Aspects](#aspects)
- [Advices implemented](#advices-implemented)
  - [@Before](#before)
  - [@After](#after)
  - [@AfterReturning](#afterreturning)
  - [@AfterThrowing](#afterthrowing)
  - [@Around](#around)

## Dependency

To use Spring AOP in our Spring Boot application, the `spring-boot-starter-aop` dependency was added in the[pom.xml](pom.xml)file.

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>
</dependencies>
```

## Aspects

Aspects are usually describe as being a cross-cutting concerns in our application, e.g., logging, transaction management and security. Since this is a demo project with a small scope and a low complexity, there aren't many cross-cutting concerns to implement aspects, thus,  we will focus on the logging concern.

```java
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect()
@Component
public class LoggingAspect {
    // Advices are added here
}
```

## Advices implemented

### @Before

This advice is executed before the method execution. 
It’s useful for tasks like logging, security, and transaction management.

In this project, the `@Before` advice is used to log the method name and its arguments before the method execution.

The pointcut of this advice targets the `com.example.springaop.service` package methods. 

```java
@Before("execution(* com.example.springaop.service.*.*(..))")
public void before(JoinPoint joinPoint) {
    String methodName = joinPoint.getSignature().toShortString();
    String args = Arrays.toString(joinPoint.getArgs());
    logger.info("Executing method: " + methodName + " with arguments: " + args);
}
```

### @After

This advice is executed after the method execution. It’s similar to the `@AfterReturning` advice, but it runs regardless of the method outcome.
It’s useful for tasks like cleaning up resources or finalizing operations.

In this project, the `@After` advice is used to log the message `HAPPY BIRTHDAY` after the method execution if the `joinedOn` date is the same as the current date. 

The pointcut of this advice targets the methods `saveEmployee` and `updateEmployee` in the `com.example.springaop.service.EmployeeService` class.

```java
@After("execution(* com.example.springaop.service.*.*(..)) && args(joinedOn,..)")
public void after(JoinPoint joinPoint, LocalDate joinedOn) {
    if (joinedOn.equals(LocalDate.now())) {
        logger.info("HAPPY BIRTHDAY!");
    }
}
```

### @AfterReturning

This advice is executed after the method execution. 
It’s useful for tasks like cleaning up resources or finalizing operations.

In this project, the `@AfterReturning` advice is used to log the method name after the method execution.

The pointcut of this advice targets the `com.example.springaop.service` package methods.

// ADD CODE HERE

### @AfterThrowing

This advice is executed if the method throws an exception. 
It’s useful for tasks like logging, sending notifications, and handling exceptions.

In this project, the `@AfterThrowing` advice is used to log the object details if an exception occurs when saving an employee. 

The pointcut of this advice targets the methods `saveEmployee` and `updateEmployee` in the `com.example.springaop.service.EmployeeService` class.

```java
@AfterThrowing(pointcut = "execution(* com.example.springaop.*.*(..))", throwing = "exception")
public void afterThrowing(JoinPoint joinPoint, Throwable exception) {
    String methodName = joinPoint.getSignature().toShortString();
    logger.error("Exception thrown by " + methodName + ": " + exception.getMessage());
}
```

### @Around

This is the most powerful advice type. It wraps around the method and can control its execution. We can modify input and output, perform additional actions before and after the method, and even prevent the method from executing.
It’s useful for tasks like logging, security, and transaction management.

For this advice type, we implemented two advices:

1. An advice to display a message before and after the method execution.

```java
import org.aspectj.lang.annotation.Around;

@Around()

```

2. An advice to measure the execution time of a method. For this purpose we will create a custom AOP annotation `@TrackTime`.

```java
@Around("@annotation(com.example.springaop.aspect.TrackTime)")
public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    Object result = joinPoint.proceed();
    long timeTaken = System.currentTimeMillis() - startTime;
    logger.info("Time taken by " + joinPoint.getSignature().toShortString() + " is " + timeTaken + "ms");
    return result;
}
```

The pointcut of both advices targets the methods in the `com.example.springaop.controller` package.
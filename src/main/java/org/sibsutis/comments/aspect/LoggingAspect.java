package org.sibsutis.comments.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(org.sibsutis.comments.api..*) || within(org.sibsutis.comments.service..*)")
    public void applicationMethods() {}

    @Before("applicationMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Executing method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "applicationMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Method executed: {} returned: {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(pointcut = "applicationMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method: {} with message: {}", joinPoint.getSignature(), exception.getMessage(), exception);
    }
}

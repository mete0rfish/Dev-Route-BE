package com.teamdevroute.devroute.global.aop.timetrace;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@Aspect
public class TimeTraceAspect {

    @Around("@annotation(com.teamdevroute.devroute.global.aop.timetrace.TimeTrace)")
    public Object traceTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            String methodName = joinPoint.getSignature().toShortString();
            log.info("{} executed in {}ms", methodName, executionTime);
        }
    }
}

package com.example.KTB_10WEEK.app.aop.aspect.log;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.example.KTB_5WEEK..service..*(..))")
    private void serviceLayer() {
    }

    @Pointcut("@annotation(com.example.KTB_5WEEK.app.aop.aspect.log.Loggable)")
    private void loggableMethods() {
    }

    @Pointcut("serviceLayer() && loggableMethods()")
    private void loggableServiceMethods() {
    }

    @Pointcut("serviceLayer() && !loggableMethods()")
    private void nonLoggableServiceMethods() {
    }

    @Before("nonLoggableServiceMethods()")
    public void logBefore(JoinPoint jp) {
        System.out.println("[Before] " + jp.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "loggableMethods()", returning = "result")
    public void logAfterReturning(JoinPoint jp, Object result) {
        System.out.println("[AfterReturning]" + jp.getSignature().toShortString() + " -> " + result);
    }

    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void logAfterThrowing(JoinPoint jp, Throwable ex) {
        System.out.println("[AfterThrowing]" + jp.getSignature().toShortString() + " 예외 : " + ex.getMessage());
    }

    @Around("loggableMethods()")
    public Object measure(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("=====================");
        long start = System.currentTimeMillis();
        System.out.println("[Around] 시작 : " + pjp.getSignature().toShortString());
        Object ret = pjp.proceed();
        long time = System.currentTimeMillis() - start;
        System.out.println("[Around] 종료 : " + pjp.getSignature().toShortString() + " 실행시간 :" + time + "ms");
        System.out.println("=====================");
        return ret;
    }
}
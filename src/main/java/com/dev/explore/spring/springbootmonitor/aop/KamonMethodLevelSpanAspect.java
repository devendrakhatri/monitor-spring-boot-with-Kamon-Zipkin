package com.dev.explore.spring.springbootmonitor.aop;

import kamon.Kamon;
import kamon.trace.Span;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class KamonMethodLevelSpanAspect {

    @Pointcut("execution(public * *(..))")
    private void anyPublicOperation() {}

    @Pointcut("execution(static public * *(..))")
    private void anyPublicStaticOperation() {}

    @Pointcut("within(com.dev.explore.spring..*)")
    private void inMyApp() {}

    @Pointcut("anyPublicOperation() && inMyApp()")
    private void myAppOperation() {}

    @Pointcut("anyPublicStaticOperation() && inMyApp()")
    private void myAppStaticOperation() {}

    @Around("myAppOperation() || myAppStaticOperation()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {

        String spanName = pjp.getSignature().getDeclaringType().getSimpleName() + "." + pjp.getSignature().getName();
        log.debug("span name: {}", spanName);
        Span span = Kamon.spanBuilder(spanName).start();

        return Kamon.runWithSpan(span, true, () -> execute(pjp));
    }

    private Object execute(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}

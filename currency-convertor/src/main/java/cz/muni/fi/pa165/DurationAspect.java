package cz.muni.fi.pa165;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.concurrent.TimeUnit;

@Named
@Aspect
public class DurationAspect {

    @Pointcut("execution(public * *(..))")
    public void allPublicMethodsPointcut(){

    }

    @Around("allPublicMethodsPointcut()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.nanoTime();
        Object retval = joinPoint.proceed();
        long end = System.nanoTime();
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Execution of " + methodName + " took " +
                TimeUnit.NANOSECONDS.toMillis(end - start) + " ms");
        return retval;
    }

}

package org.cs3219.project.peerprep.component;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class WebLogAspect {

    @Pointcut("execution(public * org.cs3219.project.peerprep.controller..*.*(..))")
    public void webLog() {}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("========================================== Start ==========================================");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes!= null) {
            HttpServletRequest request = attributes.getRequest();

            log.info("URL: {}", request.getRequestURL().toString());
            log.info("HTTP Method: {}", request.getMethod());
            log.info("Class Method: {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            log.info("IP: {}", request.getRemoteAddr());
            log.info("Request Args: {}", Arrays.toString(joinPoint.getArgs()));
        }
    }

    @AfterThrowing(value = "webLog()", throwing = "e")
    public void doAfterThrowing(Exception e) {
        log.error("Error: ", e);
    }

    @AfterReturning(value = "webLog()", returning = "response")
    public void doAfterReturning(Object response) {
        log.info("Response: {}", response);
    }

    @After("webLog()")
    public void doAfter() {
        log.info("=========================================== End ===========================================\n");
    }
}

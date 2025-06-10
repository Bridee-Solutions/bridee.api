package com.bridee.api.aop;

import com.bridee.api.service.AssessorService;
import com.bridee.api.service.CasalService;
import com.bridee.api.service.CasamentoService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
public class UserIdentifierAspect {

    @Autowired
    private CasamentoService casamentoService;

    @Autowired
    private AssessorService assessorService;

    @Autowired
    private CasalService casalService;

    @Around("within(com.bridee.api.controller.impl..*)")
    public Object userIdentifier(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = setUserIdentifier(joinPoint);
        return joinPoint.proceed(args);
    }

    private Object[] setUserIdentifier(JoinPoint joinPoint){
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Object[] args = joinPoint.getArgs();

        return setUserIdentifier(args, parameterAnnotations);
    }

    private Object[] setUserIdentifier(Object[] args, Annotation[][] parameterAnnotations){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        for (int i = 0; i < args.length; i++){
            for (Annotation annotation: parameterAnnotations[i]){
                if (annotation.annotationType().equals(WeddingIdentifier.class)){
                    args[i] = casamentoService.getWeddingIdFromCoupleEmail(email);
                    break;
                }else if(annotation.annotationType().equals(AdvisorIdentifier.class)){
                    args[i] = assessorService.getAdviserIdFromEmail(email);
                    break;
                }else if(annotation.annotationType().equals(CoupleIdentifier.class)){
                    args[i] = casalService.findIdByEmail(email);
                }
            }
        }
        return args;
    }

}

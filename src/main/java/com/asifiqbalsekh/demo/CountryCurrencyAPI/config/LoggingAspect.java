package com.asifiqbalsekh.demo.CountryCurrencyAPI.config;

import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.asifiqbalsekh.demo.CountryCurrencyAPI.controller.UserController.*(..))")
    public void loggingUserController(JoinPoint joinPoint) {
        log.info("{} Method of User Controller Called with {}.....", joinPoint.getSignature().getName()
        , joinPoint.getArgs());
    }

    @Before("execution(* com.asifiqbalsekh.demo.CountryCurrencyAPI.controller.CountryController.getAllCountries())")
    public void loggingGetCountry(){
        log.info("Before GetAllCountry.....");
    }

    @After("execution(* com.asifiqbalsekh.demo.CountryCurrencyAPI.controller.CountryController.getAllCountries())")
    public void loggingAfterGetCountry(){
        log.info("After GetAllCountry.....");
    }

    @AfterThrowing("execution(* com.asifiqbalsekh.demo.CountryCurrencyAPI.controller.UserController.*(..))")
    public void loggingUserControllerDuringException(JoinPoint joinPoint) {
        log.info("{} Method Throwing Exception.....", joinPoint.getSignature().getName());
    }

    @Around("execution(* com.asifiqbalsekh.demo.CountryCurrencyAPI.controller.UserController.loginUser(..))")
    public Object loggingUserControllerPerformance(ProceedingJoinPoint proceedingJoinPoint){
        long startTime = System.currentTimeMillis();
        Object obj=null;
        try {
            obj = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        long endTime = System.currentTimeMillis();
        log.info("Method Execution Time: {}ms", endTime - startTime);
        return obj;
    }

    @Around("execution(* com.asifiqbalsekh.demo.CountryCurrencyAPI.controller.UserController.loginUser(..)) && args(data)")
    public Object loggingUserControllerInputValidation(ProceedingJoinPoint proceedingJoinPoint, LoginRequest data){
        LoginRequest new_data;
        if(data.username().equals("asif") ){
            new_data=new LoginRequest(
                    "asif@gmail.com",
                    "asif@123"
            );
            log.info("User Intercepted,Setting Default Username & Password for asif");
        }
        else{
            new_data=data;
        }

        Object obj=null;
        try {
            obj = proceedingJoinPoint.proceed(new Object[]{new_data});
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return obj;
    }
}

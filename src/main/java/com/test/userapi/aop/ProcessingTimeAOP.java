package com.test.userapi.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;

@Aspect
@Component
public class ProcessingTimeAOP {

    final StopWatch stopWatch = new StopWatch();

    Logger logger = LoggerFactory.getLogger(ProcessingTimeAOP.class);

    @Before("execution (* com.test.userapi.controller.*.*(..))")
    public void processingTimeStart(JoinPoint joinPoint) {
        stopWatch.start();
        logger.info("Date is : {} ", LocalDateTime.now());
        logger.info("*** AOP interception : the method {} of the {} will be executed"
                ,joinPoint.getSignature().getName()
                ,joinPoint.getTarget().getClass().getSimpleName());
    }

    @After("execution (* com.test.userapi.controller.*.*(..))")
    public void processingTimeEnd(JoinPoint joinPoint) {
        stopWatch.stop();
        logger.info("Date is : {} ", LocalDateTime.now());
        logger.info("*** AOP interception : the method {} of the {}  has been excuted"
                ,joinPoint.getSignature().getName()
                ,joinPoint.getTarget().getClass().getSimpleName());
        logger.info("Processing time is : {} in  Milliseconds", stopWatch.getTotalTimeMillis());
    }
}

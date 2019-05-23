package com.hll.test.common;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

public class LogbackFlushHook {

    public void destroy(){
        ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
        if (loggerFactory instanceof LoggerContext) {
            LoggerContext context = (LoggerContext) loggerFactory;
            context.stop();
        }
    }
    
}

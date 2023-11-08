package com.denismiagkov.loggingstarter.config;

import com.denismiagkov.loggingstarter.aspects.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingAutoConfiguration {

    @Bean
    public LoggingAspect loggingAspectStarter() {
        LoggingAspect loggingAspectStarter = new LoggingAspect();
        return loggingAspectStarter;
    }
}

package com.denismiagkov.loggingstarter.annotations;

import com.denismiagkov.loggingstarter.config.LoggingAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Класс создает аннотацию, которая реализует импорт в проект бинов, сконфигурированных в стартере логирования
 * */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Import(LoggingAutoConfiguration.class)
public @interface EnableLogging {
}

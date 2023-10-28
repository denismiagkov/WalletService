//package com.denismiagkov.walletservice.infrastructure.in.servlets;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletRequestWrapper;
//import org.junit.jupiter.api.extension.ExtensionContext;
//import org.junit.jupiter.api.extension.ParameterContext;
//import org.junit.jupiter.api.extension.ParameterResolver;
//
//public class HttpServletRequestResolver implements ParameterResolver {
//
//    @Override
//    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
//        return parameterContext.getParameter().getType().equals(HttpServletRequest.class);
//    }
//
//    @Override
//    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
//        return new AppHttpServletRequest(null, "test") {
//            // Здесь можно переопределить методы HttpServletRequest по необходимости
//        };
//    }
//}

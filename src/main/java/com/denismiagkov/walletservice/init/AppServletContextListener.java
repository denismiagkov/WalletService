package com.denismiagkov.walletservice.init;

import com.denismiagkov.walletservice.configuration.Config;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebListener
public class AppServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
//        context.scan("com.denismiagkov.walletservice");
//        WebInit webInit = context.getBean("webInitBean", WebInit.class);
//        webInit.start();
    }
}
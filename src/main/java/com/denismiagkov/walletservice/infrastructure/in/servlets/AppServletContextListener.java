package com.denismiagkov.walletservice.infrastructure.in.servlets;

import com.denismiagkov.walletservice.init.WebInit;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;


@WebListener
public class AppServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WebInit.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}

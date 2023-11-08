package com.denismiagkov.walletservice.configuration;

import com.denismiagkov.walletservice.WalletServiceApplication;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WalletServiceApplication.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}

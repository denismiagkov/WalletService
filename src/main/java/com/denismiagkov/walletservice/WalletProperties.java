package com.denismiagkov.walletservice;

import java.io.*;
import java.util.Properties;

public class WalletProperties {

    File file;

    public WalletProperties() {
        this.file = new File("src/main/resources/config.properties");
    }

    public String getPropertyFromFile(String parameter) {
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream(file)) {
            property.load(fis);
            return property.getProperty(parameter);

        } catch (FileNotFoundException e) {
            System.err.println("ОШИБКА: properties-файл отсуствует!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void main(String[] args) {
        WalletProperties properties = new WalletProperties();
        System.out.println(properties.getPropertyFromFile("URL"));
    }
}

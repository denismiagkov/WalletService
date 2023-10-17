package com.denismiagkov.walletservice;

import java.io.*;
import java.util.Properties;

public class PropertyFile {
    File file;

    public PropertyFile() {
        this.file = new File("src/main/resources/config.properties");
    }

    public String getProperties(String parameter) {
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
}

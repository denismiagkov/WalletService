package com.denismiagkov.walletservice;

import java.io.*;
import java.util.Properties;

/**
 * Класс описывает конфигурационный файл, включая путь и способ извлечения данных.
 */
public class PropertyFile {
    /**
     * Файл
     */
    File file;

    /**
     * Конструктор класса без передаваемых параметров
     */
    public PropertyFile() {
        this.file = new File("src/main/resources/config.properties");
    }

    /**
     * Конструктор класса с передаваемым параметром путь к файлу
     *
     * @param file путь к файлу
     */
    public PropertyFile(File file) {
        this.file = file;
    }

    /**
     * Метод возвращает значение заданного параметра из конфигурационного файла
     *
     * @param parameter заданный параметр
     * @return значение параметра
     */
    public String getProperties(String parameter) {
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream(file)) {
            property.load(fis);
            return property.getProperty(parameter);
        } catch (FileNotFoundException e) {
            System.err.println("ОШИБКА: конфигурационный файл отсуствует!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String setProperties(String field, String parameter) {
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream(file)) {
            property.load(fis);
            property.setProperty(field, parameter);
            property.store(new BufferedWriter(new FileWriter(file)), parameter);
        } catch (FileNotFoundException e) {
            System.err.println("ОШИБКА: конфигурационный файл отсуствует!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

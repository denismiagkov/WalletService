//package com.denismiagkov.walletservice.infrastructure.in.test;
//
//import com.denismiagkov.walletservice.application.controller.Controller;
//import com.denismiagkov.walletservice.infrastructure.in.Console;
//import com.denismiagkov.walletservice.infrastructure.in.menu.MainMenu;
//import com.denismiagkov.walletservice.infrastructure.in.menu.ProfileMenu;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ConsoleTest {
//    Console console;
//    Controller controller;
//    boolean work;
//    MainMenu mainMenu;
//    ProfileMenu profileMenu;
//    ByteArrayOutputStream outContent;
//    ByteArrayInputStream inContent;
//
//    @BeforeEach
//    void setUp() {
//        console = new Console(controller);
//        this.controller = controller;
//        //this.reader = new BufferedReader(new InputStreamReader(System.in));
//        work = true;
//        mainMenu = new MainMenu(console);
//        profileMenu = new ProfileMenu(console);
//        /**
//         * Создадим объект ByteArrayOutputStream для перехвата вывода в консоль
//         * */
//        outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//    }
//
//    @Test
//    void testPrint() {
//        console.print("message");
//        assertEquals("message\n", outContent.toString());
//    }
//
//    @Test
//    void start() {
//        console.print("Выберите действие: \n" + mainMenu.print());
//        String choice = null;
//        String input = "1\n";
//        inContent = new ByteArrayInputStream(input.getBytes());
//        System.setIn(inContent);
//        try {
//            choice = reader.readLine();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        if (check(choice, mainMenu.getSize())) {
//            mainMenu.execute(Integer.parseInt(choice));
//        } else {
//            fail();
//        }
//    }
//
//    @Test
//    void startProfile() {
//    }
//
//    @Test
//    void fail() {
//    }
//
//    @Test
//    void finish() {
//    }
//
//    @Test
//    void getDataToRegisterPlayer() {
//    }
//}
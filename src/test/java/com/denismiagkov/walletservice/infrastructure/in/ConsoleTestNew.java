//package com.denismiagkov.walletservice.infrastructure.in.test;
//
//import com.denismiagkov.walletservice.application.controller.Controller;
//import com.denismiagkov.walletservice.application.service.Service;
//import com.denismiagkov.walletservice.domain.model.Player;
//import com.denismiagkov.walletservice.infrastructure.in.Console;
//import MainMenu;
//import ProfileMenu;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ConsoleTestNew {
//
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
//        controller = new Controller(new Service());
//        console = new Console(controller);
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
//    }
//
//    @Test
//    void startProfile() {
//    }
//
//    public void testCheck() {
//        assertTrue(console.check("5", 10));
//        assertTrue(console.check("10", 10));
//        assertFalse(console.check("15", 10));
//        assertFalse(console.check("-5", 10));
//        assertFalse(console.check("abc", 10));
//    }
//
//    @Test
//    void fail() {
//        console.fail();
//        assertEquals("Ошибка ввода!\n", outContent.toString());
//    }
//
//    @Test
//    void finish() {
//    }
//
//    @Test
//    void getDataToRegisterPlayer() {
//    }
//
//    @Test
//    void getDataToAuthorizePlayer() {
//    }
//
//    @Test
//    void showCurrentBalance() {
//    }
//
//    @Test
//    void callTopUpAccount() {
//    }
//
//    @Test
//    void callWriteOffFunds() {
//    }
//
//    @Test
//    void showTransactionHistory() {
//        // create a mock controller
//        Controller mockController = mock(Controller.class);
//
//        // create a test user
//        String login = "testUser";
//        String password = "testPassword";
//
//        // set up the mock controller to return a transaction history for the test user
//        String expectedTransactionHistory = "transaction 1\ntransaction 2\ntransaction 3";
//        when(mockController.getTransactionsHistory(login, password)).thenReturn(expectedTransactionHistory);
//
//        // create an instance of the class under test
//        MyClass myClass = new MyClass(mockController);
//
//        // call the method under test
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//        myClass.showTransactionHistory(login, password);
//        String actualOutput = outContent.toString().trim();
//
//        // verify that the output is correct
//        String expectedOutput = "История транзакций по Вашему счету: " + expectedTransactionHistory;
//        assertEquals(expectedOutput, actualOutput);
//    }
//}
//

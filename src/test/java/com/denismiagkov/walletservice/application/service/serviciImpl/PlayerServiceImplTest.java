package com.denismiagkov.walletservice.application.service.serviciImpl;

import com.denismiagkov.walletservice.application.service.serviceImpl.PlayerServiceImpl;
import com.denismiagkov.walletservice.application.service.serviceImpl.exception.IncorrectLoginException;
import com.denismiagkov.walletservice.application.service.serviceImpl.exception.IncorrectPasswordException;
import com.denismiagkov.walletservice.application.service.serviceImpl.exception.LoginIsNotUniqueException;
import com.denismiagkov.walletservice.application.service.serviceImpl.exception.PlayerAlreadyExistsException;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.repository.PlayerDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlayerServiceImplTest {
    PlayerServiceImpl psi;
    private Set<Player> allPlayers;
    private Map<String, String> allEntries;
    private Map<String, Player> loginsPerPlayers;

    PlayerDAOImpl pdi;

    @BeforeEach
    void setUp() {
        psi = new PlayerServiceImpl();
        this.allPlayers = new HashSet<>();
        this.allEntries = new HashMap<>();
        this.loginsPerPlayers = new HashMap<>();
        this.pdi = new PlayerDAOImpl();

    }

    @Test
    void getAllPlayers() {
        psi.registerPlayer("Sidor", "Ivanov", "123@gmail.com", "igrok", "345");
        Player player = new Player("Sidor", "Ivanov", "123@gmail.com");
        assertTrue(pdi.getAllPlayers().contains(player));
    }

//    @Test
//    void getAllEntries() {
//        psi.registerPlayer("Sidor", "Ivanov", "123@gmail.com", "igrok", "345");
//        Player player = new Player("Sidor", "Ivanov", "123@gmail.com");
//        assertEquals("345", psi.getAllEntries().get("igrok"));
//    }
//
//    @Test
//    void getLoginsPerPlayers() {
//        psi.registerPlayer("Sidor", "Ivanov", "123@gmail.com", "igrok", "345");
//        assertEquals("Sidor", psi.getLoginsPerPlayers().get("igrok").getFirstName());
//    }
//
//    private static Player getPlayer() {
//        Player player = new Player("Ivan", "Petrov", "123@mail.ru");
//        return player;
//    }
//
//    @Test
//    void testRegisterPlayer_PlayerAlreadyExistsException(){
//        psi.registerPlayer("Petr", "Ivanov", "123@mail.ru", "petya", "987");
//        assertThrows(PlayerAlreadyExistsException.class, ()-> psi.registerPlayer("Petr",
//                "Ivanov", "123@mail.ru", "nick", "123"));
//    }
//
//    @Test
//    void testRegisterPlayer_LoginIsNotUniqueException(){
//        psi.registerPlayer("Petr", "Ivanov", "123@mail.ru", "person", "987");
//        assertThrows(LoginIsNotUniqueException.class, ()-> psi.registerPlayer("Sergei",
//                "Sidorov", "ser@mail.ru", "person", "123"));
//    }
//
//    @Test
//    void testRegisterPlayer_PlayerCreation(){
//        psi.registerPlayer("Petr", "Ivanov", "123@mail.ru", "person", "987");
//        Player player = new Player("Petr", "Ivanov", "123@mail.ru");
//        assertTrue(psi.getAllPlayers().contains(player));
//    }
//    @Test
//    void testRegisterPlayer_entryCreation(){
//        psi.registerPlayer("Petr", "Ivanov", "123@mail.ru", "person", "987");
//        assertEquals("987", psi.getAllEntries().get("person"));
//        assertEquals("Petr", psi.getLoginsPerPlayers().get("person").getFirstName());
//    }
//
//    @Test
//    void authorizePlayer() {
//        psi.registerPlayer("Petr", "Ivanov", "123@mail.ru", "person", "987");
//        Player player = psi.authorizePlayer("person", "987");
//        assertTrue(player!=null);
//        assertEquals("Petr", psi.getLoginsPerPlayers().get("person").getFirstName());
//    }

    @Test
    void authorizePlayer_IncorrectLoginException(){
        psi.registerPlayer("Petr", "Ivanov", "123@mail.ru", "person", "987");
        assertThrows(IncorrectLoginException.class, ()-> psi.authorizePlayer("person1", "987"));
    }

    @Test
    void authorizePlayer_IncorrectPasswordException(){
        psi.registerPlayer("Petr", "Ivanov", "123@mail.ru", "person", "987");
        assertThrows(IncorrectPasswordException.class, ()-> psi.authorizePlayer("person", "887"));
    }
}
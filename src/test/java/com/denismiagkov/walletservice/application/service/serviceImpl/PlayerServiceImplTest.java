package com.denismiagkov.walletservice.application.service.serviceImpl;

import com.denismiagkov.walletservice.application.dto.PlayerDto;
import com.denismiagkov.walletservice.application.dto.PlayerMapper;
import com.denismiagkov.walletservice.application.service.serviceImpl.exceptions.LoginIsNotUniqueException;
import com.denismiagkov.walletservice.application.service.serviceImpl.exceptions.PlayerAlreadyExistsException;
import com.denismiagkov.walletservice.domain.model.Entry;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.repository.interfaces.PlayerDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anySet;

class PlayerServiceImplTest {

    PlayerServiceImpl playerService;

    PlayerDAO playerDAO;
    String login;

    @BeforeEach
    void prepare() {
        playerDAO = Mockito.mock(PlayerDAO.class);
        playerService = new PlayerServiceImpl(playerDAO);
        login = "Ivan";
    }

    @Test
    void registerPlayer_ShouldThrowPlayerAlreadyExistsException() {
        Player player = new Player("Ivan", "Petrov", "123@mail.ru");
        Entry entry = new Entry(1, "login", "password");
        PlayerDto playerDto = PlayerMapper.INSTANCE.toPlayerDto(player, entry);
        // Имитируем присутствие игрока в БД
        Set<Player> players = new HashSet<>();
        players.add(player);
        Mockito.doReturn(players).when(playerDAO).getAllPlayers();

        Assertions.assertThrows(PlayerAlreadyExistsException.class, ()-> playerService.registerPlayer(playerDto));
    }

    @Test
    void registerPlayer_ShouldReturnPlayer() {
        Player player = new Player("Ivan", "Petrov", "123@mail.ru");
        Entry entry = new Entry(1, "login", "password");
        PlayerDto playerDto = PlayerMapper.INSTANCE.toPlayerDto(player, entry);
        Mockito.doReturn(player).when(playerDAO).savePlayer(player);
        assertEquals(player, playerService.registerPlayer(playerDto));
    }

    @Test
    void getPlayerId_Should_Return_Correct_Id() {
        Mockito.doReturn(1).when(playerDAO).getPlayerId(login);
        int id = playerService.getPlayerId(login);
        assertEquals(1, id);
    }

    @Test
    void getPlayerByLogin__Should_Return_Correct_Id() {
        Player player = new Player("Ivan", "Petrov", "123@mail.ru");
        Mockito.doReturn(player).when(playerDAO).getPlayerByLogin(login);
        assertEquals(player, playerService.getPlayerByLogin(login));
    }

    @Test
    void getEntryByLogin() {
        Entry entry = new Entry(1, "ivan", "123");
        Mockito.doReturn(entry).when(playerDAO).getEntryByLogin(login);
        assertEquals(entry, playerService.getEntryByLogin(login));
    }

    @Test
    void isPlayerExist_ShouldReturnTrue() {
        Player player = new Player("Ivan", "Petrov", "123@mail.ru");
        Set<Player> players = new HashSet<>();
        players.add(player);
        Mockito.doReturn(players).when(playerDAO).getAllPlayers();
        assertTrue(playerService.isPlayerExist(player));
    }

    @Test
    void isPlayerExist_ShouldReturnFalse() {
        Player player1 = new Player("Ivan", "Petrov", "123@mail.ru");
        Player player2 = new Player("Petr", "Ivanov", "321@mail.ru");
        Set<Player> players = new HashSet<>();
        players.add(player1);
        Mockito.doReturn(players).when(playerDAO).getAllPlayers();
        assertFalse(playerService.isPlayerExist(player2));
    }

    @Test
    void isLoginExist_ShouldReturnTrue() {
        String login = "Ivan";
        String password = "123";
        Map<String, String> allEntries = new HashMap<>();
        allEntries.put(login, password);
        Mockito.doReturn(allEntries).when(playerDAO).getAllEntries();
        assertTrue(playerService.isLoginExist("Ivan"));
    }

    @Test
    void isLoginExist_ShouldReturnFalse() {
        String login = "Ivan";
        String password = "123";
        Map<String, String> allEntries = new HashMap<>();
        allEntries.put(login, password);
        Mockito.doReturn(allEntries).when(playerDAO).getAllEntries();
        assertFalse(playerService.isLoginExist("Petr"));
    }
}
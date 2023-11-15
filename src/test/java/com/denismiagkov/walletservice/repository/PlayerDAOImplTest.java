package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.domain.model.Entry;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.infrastructure.liquibase.LiquibaseApp;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class PlayerDAOImplTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:12.16"
    );

    DatabaseConnection dbConnection = new DatabaseConnection(
            postgres.getJdbcUrl(),
            postgres.getUsername(),
            postgres.getPassword(),
            postgres.getDriverClassName());
    Connection connection;

    {
        try {
            connection = dbConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    LiquibaseApp liquibaseApp = new LiquibaseApp(dbConnection);
    PlayerDAOImpl playerDAO;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() throws SQLException {
        liquibaseApp.start();
        playerDAO = new PlayerDAOImpl(dbConnection);
        connection.setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
    }

    @Test
    @Disabled("flaky, need to see")
    @RepeatedTest(value = 5)
    void savePlayer_COUNT_in_players_increased_by_1_after_insert_player() {
        // Посмотрим, сколько записей о игроках содержится в базе данных до сохранения нового игрока
        Set<Player> allPlayers = playerDAO.getAllPlayers();
        assertEquals(2, allPlayers.size());
        // Сохраним в базе денных сведения о новом игроке
        playerDAO.savePlayer(new Player("Ivan", "Petrov", "ipetrov@mail.ru"));
        // Посмотрим, сколько записей о игроках содержится в базе данных после сохранения нового игрока
        allPlayers = playerDAO.getAllPlayers();
        assertEquals(3, allPlayers.size());
    }

    @Test
    @Disabled("flaky, need to see")
        // @RepeatedTest(value = 5)
    void saveEntry_COUNT_in_entries_increased_by_1_after_insert_entry() {
        //Сохраняем в БД игрока, чтобы не нарушить ForeignKey в таблице entries
        playerDAO.savePlayer(new Player("Nestor", "Sidorov", "nsidorov@mail.ru"));
        //Проверяем, что перед добавлением entry в таблице entries содержатся 2 записи
        assertEquals(2, playerDAO.getAllEntries().size());
        // Добавляем новую запись в таблицу и проверяем, что количество записей возросло на 1
        Entry entry = new Entry(3, "login3", "password3");
        playerDAO.saveEntry(entry);
        assertEquals(3, playerDAO.getAllEntries().size());
    }

    @Test
    @Disabled("flaky, need to see")
    void getAllPlayers_Size_of_set_of_players_equals_2() {
        Set<Player> players = playerDAO.getAllPlayers();
        //Проверяем, что метод вернул объект - объект не null и содержит 2 записи
        assertNotNull(players);
        assertEquals(2, players.size());
    }

    @Test
    @Disabled("flaky, need to see")
    void getAllEntries_Size_of_set_of_entries_equals_2() {
        //Проверяем, что метод вернул объект, который содержит 2 записи
        assertEquals(2, playerDAO.getAllEntries().size());
    }

    @Test
    void getPlayerId_Id_of_new_added_player_equals_3() {
        //Проверяем, что  в таблице players содержатся 2 записи
        assertEquals(2, playerDAO.getAllPlayers().size());
        // Добавляем третьего игрока и его логин и пароль (entry)
        Player player = new Player("Ivan", "Petrov", "ipetrov@mail.ru");
        playerDAO.savePlayer(player);
        playerDAO.saveEntry(new Entry(3, "login3", "password3"));
        //Проверяем, что id нового игрока = 3
        assertEquals(3, playerDAO.getPlayerId(player));
    }

    @Test
    void GetPlayerId_Id_equals_2() {
        assertEquals(2, playerDAO.getPlayerId("login2"));
    }

    @Test
    void getPlayerById_Name_surname_email_of_returned_player_are_correct() {
        assertAll(
                () -> assertEquals("Denis", playerDAO.getPlayerById(1).getFirstName()),
                () -> assertEquals("Miagkov", playerDAO.getPlayerById(1).getLastName()),
                () -> assertEquals("tomich-84@mail.ru", playerDAO.getPlayerById(1).getEmail())
        );
    }

    @Test
    void getPlayerByLogin_Name_surname_email_of_returned_player_are_correct() {
        assertAll(
                () -> assertEquals("Denis", playerDAO.getPlayerByLogin("login1").getFirstName()),
                () -> assertEquals("Miagkov", playerDAO.getPlayerByLogin("login1").getLastName()),
                () -> assertEquals("tomich-84@mail.ru", playerDAO.getPlayerByLogin("login1").getEmail())
        );
    }

    @Test
    void getEntryByLogin_Id_login_password_are_correct() {
        Entry entry = new Entry(1, "login1", "password1");
        assertEquals(entry, playerDAO.getEntryByLogin("login1"));
    }
}
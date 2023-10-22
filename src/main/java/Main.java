import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.application.service.serviceImpl.AccountServiceImpl;
import com.denismiagkov.walletservice.application.service.serviceImpl.OperationServiceImpl;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.dto.PlayerDto;
import com.denismiagkov.walletservice.dto.PlayerMapper;
import com.denismiagkov.walletservice.dto.TransactionDto;
import com.denismiagkov.walletservice.dto.TransactionMapper;
import com.denismiagkov.walletservice.infrastructure.in.Console;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import com.denismiagkov.walletservice.infrastructure.liquibase.LiquibaseApp;
import com.denismiagkov.walletservice.repository.PlayerDAOImpl;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.sql.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Это класс Main - вход в программу
 */
public class Main {
    /**
     * Это метод  main() - точка входа в программу.
     */
    public static void main(String[] args) throws SQLException {

        DatabaseConnection dbConnection = new DatabaseConnection();
        LiquibaseApp liquibaseApp = new LiquibaseApp(dbConnection);
        liquibaseApp.start();

        Service service = new Service();
        Controller controller = new Controller(service);
//        Console console = new Console(controller);
//        console.start();
        AccountServiceImpl accountService = new AccountServiceImpl();
        TransactionMapper mapper = Mappers.getMapper(TransactionMapper.class);
        List<Transaction> list = accountService.getTransactionHistory(1);
        List<Transaction> transactionDtoList = mapper.toTransactionDtoList(list);
        System.out.println(transactionDtoList);

    }
}

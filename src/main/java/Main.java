import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.application.service.serviceImpl.AccountServiceImpl;
import com.denismiagkov.walletservice.application.service.serviceImpl.OperationServiceImpl;
import com.denismiagkov.walletservice.application.service.serviceImpl.PlayerServiceImpl;
import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Operation;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.dto.*;
import com.denismiagkov.walletservice.infrastructure.in.Console;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import com.denismiagkov.walletservice.infrastructure.liquibase.LiquibaseApp;
import com.denismiagkov.walletservice.repository.AccountDAOImpl;
import com.denismiagkov.walletservice.repository.OperationDAOImpl;
import com.denismiagkov.walletservice.repository.PlayerDAOImpl;
import io.jsonwebtoken.security.Keys;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.sql.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

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
//        PlayerDAOImpl playerService = new PlayerDAOImpl();
//        Player player = playerService.getPlayer(1);
//        PlayerMapper mapper = Mappers.getMapper(PlayerMapper.class);
//        PlayerDto playerDto = mapper.toPlayerDto(player);
//        System.out.println(playerDto.getSurname());

//        AccountDAOImpl accountService = new AccountDAOImpl();
//        Account account = accountService.getAccount(1);
//        AccountMapper mapper = Mappers.getMapper(AccountMapper.class);
//        AccountDto accountDto = mapper.toAccountDto(account);
//        System.out.println(account.getNumber());

//        OperationDAOImpl operationDAO = new OperationDAOImpl();
//        Operation operation = operationDAO.getOperation(1);
//        System.out.println(operation);
//
//        OperationMapper operationMapper= Mappers.getMapper(OperationMapper.class);
//        OperationDto operationDto = operationMapper.toOperationDto(operation);
//        System.out.println(operationDto.getTime());
//        List<OperationDto> list = operationMapper.toOperationDtoList(operationDAO.getLog());
//        System.out.println(list.get(1).getType());


    }
}

class GenerateKeys {

    public static void main(String[] args) {
        System.out.println(generateKey());
        System.out.println(generateKey());
    }

    private static String generateKey() {
        return Encoders.BASE64.encode(Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded());
    }

}

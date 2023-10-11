import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.infrastructure.in.Console;

/**
 * Это точка входа в программу
 * */
public class Main {
    /**
     * Это класс  main()
     * */
    public static void main(String[] args) {
        /**
         * Инициализируем необходимые службы и запускаем приложение
         * */
        Service service = new Service();
        Controller controller = new Controller(service);
        Console console = new Console(controller);
        console.start();


    }
}

package output;

/**
 * Класс, предназначенный для вывода данных из консоли.
 */
public class ConsoleOutputManager implements OutputManager {
    /**
     * Печатает сообщение в консоль.
     * @param message печатуемое сообщение.
     */
    @Override
    public void printlnMessage(String message) {
        System.out.println(message);
        System.out.flush();
    }

    /**
     * Печатает сообщение об ошибке в консоль.
     * @param errorMessage печатуемое сообщение.
     */
    @Override
    public void printlnErrorMessage(String errorMessage) {
        System.err.println(errorMessage);
        System.err.flush();
    }
}

package output;

/**
 * Класс, предназначенный для вывода данных из консоли.
 */
public class ConsoleOutputManager implements OutputManager {

    @Override
    public void printMessage(String message) {
        System.out.print(message);
        System.out.flush();
    }

    @Override
    public void printlnMessage(String message) {
        System.out.println(message);
        System.out.flush();
    }

    @Override
    public void printlnErrorMessage(String errorMessage) {
        System.err.println(errorMessage);
        System.err.flush();
    }
}

package input;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Класс, предназначенный для чтения данных из консоли.
 */
public class ConsoleInputManager implements InputManager {
    private BufferedReader bufferedReader;

    /**
     * Конструктор.
     * @param bufferedReader объект типа BufferedReader.
     */
    public ConsoleInputManager(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    /**
     * @return bufferedReader.readLine()
     */
    @Override
    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }

    /**
     * @return bufferedReader.ready()
     */
    @Override
    public boolean readyToRead() throws IOException {
        return bufferedReader.ready();
    }
}

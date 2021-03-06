package input;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Класс, предназначенный для чтения данных из консоли.
 */
public class ConsoleInputManager implements InputManager {
    private BufferedReader bufferedReader;

    public ConsoleInputManager(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }

    @Override
    public boolean readyToRead() throws IOException {
        return bufferedReader.ready();
    }
}

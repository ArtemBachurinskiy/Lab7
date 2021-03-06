package input;

import java.io.IOException;
import java.util.Scanner;

/**
 * Класс, предназначенный для чтения файлов-скриптов.
 */
public class ScriptInputManager implements InputManager {
    private Scanner scanner;

    public ScriptInputManager(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String readLine() throws IOException {
        return scanner.nextLine();
    }

    /**
     * Проверяет, готов ли даннный ScriptInputManager считывать данные.
     * @return scanner.hasNextLine()
     */
    @Override
    public boolean readyToRead() throws IOException {
        return scanner.hasNextLine();
    }
}

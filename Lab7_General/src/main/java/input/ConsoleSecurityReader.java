package input;

import output.OutputManager;

import java.io.IOException;

public class ConsoleSecurityReader {
    private InputManager inputManager;
    private OutputManager outputManager;

    public ConsoleSecurityReader(InputManager inputManager, OutputManager outputManager) {
        this.inputManager = inputManager;
        this.outputManager = outputManager;
    }

    public String readUsername() throws IOException {
        String user;
        do {
            outputManager.printMessage("username: ");
            user = inputManager.readLine();
            if (user.isEmpty())
                outputManager.printlnMessage("Имя пользователя не может быть пустым! Повторите ввод.");
        } while (user.isEmpty());
        return user.trim();
    }

    public String readPassword() throws IOException {
        String pw;
        do {
            outputManager.printMessage("password: ");
            pw = inputManager.readLine();
            if (pw.isEmpty())
                outputManager.printlnMessage("Пароль не может быть пустым! Повторите ввод.");
        } while (pw.isEmpty());
        return pw.trim();
    }
}
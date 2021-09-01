package commands;

import application.ClientApplication;

/**
 * Класс команды exit.
 * Команда exit завершает работу клиентского приложения.
 */
public class ExitCommand implements ClientCommand {
    private ClientApplication application;

    ExitCommand (ClientApplication application) {
        this.application = application;
    }

    @Override
    public void execute(String argument) {
        application.setExit();
    }
}

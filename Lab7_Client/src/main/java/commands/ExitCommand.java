package commands;

import application.ClientApplication;

/**
 * Класс команды exit.
 * Команда exit завершает работу клиентского приложения.
 */
public class ExitCommand implements ClientCommand {
    private ClientApplication application;

    /**
     * @param application объект данного клиентского приложения
     */
    ExitCommand (ClientApplication application) {
        this.application = application;
    }

    @Override
    public void execute(String argument) {
        application.setExit();
    }
}

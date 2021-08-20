package commands;

import java.io.IOException;

/**
 * Интрфейс ClientCommand является базовым интерфейсом для всех команд, исполняемых на клиенте.
 * Данный интерфейс реализует шаблон проектирования "Command".
 */
public interface ClientCommand {
    /**
     * Метод, в котором реализована команда.
     * @param argument аргумент команды, если он требуется
     */
    void execute(String argument) throws IOException;
}
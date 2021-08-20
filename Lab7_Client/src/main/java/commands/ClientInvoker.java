package commands;

import java.io.IOException;

/**
 * Класс клиентского инвокера команд. Этот класс предназначен для вызова одной конкретной команды.
 */
class ClientInvoker {
    private ClientCommand command;

    /**
     * @param command Конкретная команда.
     */
    ClientInvoker(ClientCommand command) {
        this.command = command;
    }

    /**
     * @param argument аргумент, который требуется конкретной команде.
     */
    void executeCommand(String argument) throws IOException {
        command.execute(argument);
    }
}

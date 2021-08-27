package commands;

import request.Request;
import response.Response;

import java.util.List;

/**
 * Класс команды history.
 * Описание команды: вывести последние 9 команд (без их аргументов)
 */
public class HistoryCommand implements ServerCommand {
    private List<String> commandsOfClientHistory;
    private final int LIMIT;

    HistoryCommand (List<String> commandsOfClientHistory, final int LIMIT) {
        this.commandsOfClientHistory = commandsOfClientHistory;
        this.LIMIT = LIMIT;
    }

    @Override
    public Response execute(Request request) {
        StringBuilder message = new StringBuilder();
        if (commandsOfClientHistory.size() == LIMIT)
            message.append("Последние ")
                    .append(LIMIT)
                    .append(" исполненных команд :\n");
        else
            message.append("Последние исполненные команды (")
                    .append(commandsOfClientHistory.size())
                    .append(") :\n");
        commandsOfClientHistory.forEach(command -> message.append(command).append('\n'));
        return new Response(request.getCommand(), message.toString(), true);
    }

    public String getDescription() {
        return "history : вывести последние 9 команд (без их аргументов)";
    }
}

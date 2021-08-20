package commands;

import request.Request;
import response.Response;

import java.util.Map;
import java.util.Set;

/**
 * Класс команды help.
 * Описание команды: вывести справку по доступным командам
 */
public class HelpCommand implements ServerCommand {
    private Set<String> commandNames;
    private Map<String, ServerCommand> commandsOfClient;

    HelpCommand (Set<String> commandNames, Map<String, ServerCommand> commandsOfClient) {
        this.commandNames = commandNames;
        this.commandsOfClient = commandsOfClient;
    }

    @Override
    public Response execute(Request request) {
        StringBuilder message = new StringBuilder();
        message.append("Справка по доступным командам!\n")
                .append("execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n")
                .append("exit : завершить программу (без сохранения в файл)\n");
        commandNames.forEach(commandName -> message.append(commandsOfClient.get(commandName).getDescription())
                                                    .append("\n"));

        return new Response(request.getCommand(), message.toString());
    }

    public String getDescription() {
        return "help : вывести справку по доступным командам";
    }
}

package commands;

import application.ServerApplication;
import collection.CollectionManager;
import database.DBConnector;
import database.DBReader;
import database.DBWriter;
import output.OutputManager;
import request.Request;
import response.Response;

import java.util.*;

public class ServerCommandManager {
    private OutputManager outputManager;
    private Map<String, ServerCommand> commandsOfClient;
    private Map<String, ServerCommand> commandsOfServer;
    private List<String> commandsOfClientHistory = new ArrayList<>();
    private final int LIMIT = 9;

    public ServerCommandManager(ServerApplication application, OutputManager outputManager, CollectionManager collectionManager,
                                DBWriter dbWriter, DBReader dbReader, DBConnector dbConnector) {
        this.outputManager = outputManager;
        commandsOfClient = new HashMap<>();
        commandsOfClient.put("help", new HelpCommand(commandsOfClient.keySet(), commandsOfClient));
        commandsOfClient.put("info", new InfoCommand(collectionManager));
        commandsOfClient.put("show", new ShowCommand(collectionManager));
        commandsOfClient.put("insert", new InsertCommand(collectionManager, dbWriter, dbReader));
        commandsOfClient.put("update", new UpdateCommand(collectionManager, dbWriter, dbReader));
        commandsOfClient.put("remove_key", new RemoveKeyCommand(collectionManager, dbWriter));
        commandsOfClient.put("clear", new ClearCommand(collectionManager, dbWriter));
        commandsOfClient.put("remove_greater", new RemoveGreaterCommand(collectionManager, dbWriter));
        commandsOfClient.put("remove_lower", new RemoveLowerCommand(collectionManager, dbWriter));
        commandsOfClient.put("history", new HistoryCommand(commandsOfClientHistory, LIMIT));
        commandsOfClient.put("max_by_golden_palm_count", new MaxByGoldenPalmCountCommand(collectionManager));
        commandsOfClient.put("print_ascending", new PrintAscendingCommand(collectionManager));
        commandsOfClient.put("print_field_ascending_golden_palm_count", new PrintFieldAscendingGoldenPalmCountCommand(collectionManager));

        commandsOfServer = new HashMap<>();
        commandsOfServer.put("shutdown", new ShutdownCommand(application));
    }

    //todo:
    // -> Причём команды update, remove_key, clear, remove_greater, remove_lower должны исполняться ТОЛЬКО
    // для записей конкретного пользователя

    //todo: команда insert работает неправильно. В коллекцию неправильно вставляются элементы, когда их набралось
    // несколько. Но в БД всё отображается правильно

    //todo: команда history должна выводить историю только тех команд, которые вводит конкретный клиент (тот, кот. авторизовался)
    public Response manageRequestOfClient(Request request) {
        if (request == null)
            return null;
        String command = request.getCommand();
        if (commandsOfClient.containsKey(command)) {
            updateCommandsOfClientHistory(command);
            try {
                ServerInvoker invoker = new ServerInvoker(commandsOfClient.get(command));
                return invoker.executeCommand(request);
            } catch (NumberFormatException e) {
                return new Response(null, "Неверный тип аргумента!");
            }
        }
        else
            return new Response(null, "Неопознанная команда. Наберите help для справки.");
    }
    
    public void manageRequestOfServer (Request request) {
        String command = request.getCommand();
        if (commandsOfServer.containsKey(command)) {
            ServerInvoker invoker = new ServerInvoker(commandsOfServer.get(command));
            invoker.executeCommand(request);
        }
    }

    private void updateCommandsOfClientHistory(String command) {
        if (commandsOfClientHistory.size() < LIMIT)
            commandsOfClientHistory.add(command);
        else {
            commandsOfClientHistory.remove(0);
            commandsOfClientHistory.add(command);
        }
    }

    public String getCommandsOfServerNames() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Map.Entry<String, ServerCommand>> iterator = commandsOfServer.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ServerCommand> entry = iterator.next();
            stringBuilder.append(entry.getKey());
            if (iterator.hasNext())
                stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }
}
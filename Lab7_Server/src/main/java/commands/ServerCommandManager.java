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
    private DBReader dbReader;
    private Map<String, ServerCommand> commandsOfClient;
    private Map<String, ServerCommand> commandsOfServer;
    private List<String> commandsOfClientHistory = new ArrayList<>();
    private final int LIMIT = 9;

    public ServerCommandManager(ServerApplication application, OutputManager outputManager, CollectionManager collectionManager,
                                DBWriter dbWriter, DBReader dbReader, DBConnector dbConnector) {
        this.outputManager = outputManager;
        this.dbReader = dbReader;
        commandsOfClient = new HashMap<>();
        commandsOfClient.put("help", new HelpCommand(commandsOfClient.keySet(), commandsOfClient));
        commandsOfClient.put("info", new InfoCommand(collectionManager));
        commandsOfClient.put("show", new ShowCommand(collectionManager));
        commandsOfClient.put("insert", new InsertCommand(collectionManager, dbWriter, dbReader));
        commandsOfClient.put("update", new UpdateCommand(collectionManager, dbWriter, dbReader));
        commandsOfClient.put("remove_key", new RemoveKeyCommand(collectionManager, dbWriter, dbReader));
        commandsOfClient.put("clear", new ClearCommand(collectionManager, dbWriter, dbReader));
        commandsOfClient.put("remove_greater", new RemoveGreaterCommand(collectionManager, dbWriter));
        commandsOfClient.put("remove_lower", new RemoveLowerCommand(collectionManager, dbWriter));
        commandsOfClient.put("history", new HistoryCommand(commandsOfClientHistory, LIMIT));
        commandsOfClient.put("max_by_golden_palm_count", new MaxByGoldenPalmCountCommand(collectionManager));
        commandsOfClient.put("print_ascending", new PrintAscendingCommand(collectionManager));
        commandsOfClient.put("print_field_ascending_golden_palm_count", new PrintFieldAscendingGoldenPalmCountCommand(collectionManager));
        commandsOfClient.put("login", new LoginCommand(dbReader));
        commandsOfClient.put("register", new RegisterCommand(dbWriter));

        commandsOfServer = new HashMap<>();
        commandsOfServer.put("shutdown", new ShutdownCommand(application));
    }

    //todo: команда insert работает неправильно. В КОЛЛЕКЦИЮ НАЧИНАЮТ ВСТАВЛЯТЬСЯ ОДНИ И ТЕ ЖЕ ЭЛЕМЕНТЫ, когда их набралось
    // несколько. Но в БД всё отображается правильно. Если серверу не написать shutdown, то при подключении клиента так и будут выводится
    // неправильные эл-ты при исполнении команды show

    //todo: команда history должна выводить историю только тех команд, которые вводит конкретный клиент (тот, кот. авторизовался)

    //todo: команда help выводит устаревшую справку: нужно ОБНОВИТЬ МЕТОДЫ getDescription() в каждом классе команд.

    //todo: команда show должна выводить и имя пользователя в том числе(создавшего объект)

    //todo: обновить JavaDoc, удалить очевидные комментарии (например комментарии к конструкторам)

    //todo: проверить, что работает команда execute_script правильно

    //_______

    //todo: CLIENT ON, SERVER ON > серверные команды (shutdown) срабатывают только после того, как клиент послал к-н команду
    // (т.е. не работает non-blocking сервер)

    //todo: CLIENT ON, SERVER включается и выключается > после того, как сервер отключился, при попытке написать запрос на клиенте
    // клиент зависает. 1 раз напишет, что не удалось послать запрос и зависает. Но exit срабатывает

    public Response manageRequestOfClient(Request request) {
        if (request == null)
            return null;
        String command = request.getCommand();
        if (commandsOfClient.containsKey(command)) {
            if (!command.equals("register")) {
                String validationResult = dbReader.validateUser(request.getUsername(), request.getPassword());
                if (!validationResult.equals(dbReader.getValidateUserOK()))
                    return new Response(null, validationResult, false);
            }
            updateCommandsOfClientHistory(command);
            try {
                ServerInvoker invoker = new ServerInvoker(commandsOfClient.get(command));
                return invoker.executeCommand(request);
            } catch (NumberFormatException e) {
                return new Response(null, "Неверный тип аргумента!", true);
            }
        }
        else
            return new Response(null, "Неопознанная команда. Наберите help для справки.", true);
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
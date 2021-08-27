package commands;

import application.ClientApplication;
import connect.ClientConnectionManager;
import input.ConsoleSecurityReader;
import input.InputManager;
import output.OutputManager;
import request.Request;
import response.Response;
import entities.Movie;
import builders.MovieBuilder;
import request.ClientRequestSender;
import response.ClientResponseReceiver;
import script.ScriptFilesManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, содержащий HashMap клиентских команд и занимающийся их обработкой.
 */
public class ClientCommandManager {
    private Map<String, ClientCommand> clientCommandMap;
    private OutputManager outputManager;
    private InputManager inputManager;
    private ClientConnectionManager clientConnectionManager;
    private ClientRequestSender clientRequestSender;
    private ClientResponseReceiver clientResponseReceiver;
    private MovieBuilder movieBuilder;
    private ConsoleSecurityReader consoleSecurityReader;
    private boolean isSent;
    private String username;
    private String password;

    /**
     * Конструктор класса ClientCommandManager.
     * @param application объект данного клиентского приложения
     * @param outputManager менеджер вывода данных
     * @param scriptFilesManager менеджер по управлению файлами-скриптами
     * @param clientConnectionManager клиентский менеджер установки соединения
     * @param clientRequestSender клиенткий менеджер отправки запросов
     * @param clientResponseReceiver клиенткий менеджер получения ответов
     * @param movieBuilder объект, предназначенный для создания объекта Movie
     */
    public ClientCommandManager(ClientApplication application, OutputManager outputManager, InputManager inputManager,
                                ScriptFilesManager scriptFilesManager, ClientConnectionManager clientConnectionManager,
                                ClientRequestSender clientRequestSender, ClientResponseReceiver clientResponseReceiver,
                                MovieBuilder movieBuilder) {
        this.outputManager = outputManager;
        this.inputManager = inputManager;
        this.clientConnectionManager = clientConnectionManager;
        this.clientRequestSender = clientRequestSender;
        this.clientResponseReceiver = clientResponseReceiver;
        this.movieBuilder = movieBuilder;
        this.consoleSecurityReader = new ConsoleSecurityReader(inputManager, outputManager);
        this.username = null;
        this.password = null;

        clientCommandMap = new HashMap<>();
        clientCommandMap.put("execute_script", new ExecuteScriptCommand(application, outputManager, scriptFilesManager, clientConnectionManager, clientRequestSender, clientResponseReceiver, movieBuilder));
        clientCommandMap.put("exit", new ExitCommand(application));
    }

    /**
     * Метод, который выбирает команду из clientCommandMap и исполняет её посредством ClientInvoker.
     * Если в clientCommandMap такой команды нет, то она отправляется для обработки на сервер.
     * @param command команда, которую нужно обработать
     * @param argument аргумент команды
     */
    public void manageCommand (String command, String argument) throws IOException {
        if (command.isEmpty())
            return;
        command = command.toLowerCase();
        if (clientCommandMap.containsKey(command)) {
            ClientInvoker invoker = new ClientInvoker(clientCommandMap.get(command));
            invoker.executeCommand(argument);
        }
        else {
            if (clientConnectionManager.connectionIsEstablished()) {
                Movie movie = null;

                if (command.equals("login") || command.equals("register")) {
                    username = consoleSecurityReader.readUsername();
                    password = consoleSecurityReader.readPassword();
                }
                if (command.equals("insert"))
                    movie = movieBuilder.buildMovie(0, argument);
                try {
                    if (command.equals("update"))
                        movie = movieBuilder.buildMovie(Integer.parseInt(argument));
                }
                catch (NumberFormatException e) {
                    outputManager.printlnMessage("Неверный тип аргумента!");
                    return;
                }

                isSent = clientRequestSender.sendRequestToServer(new Request(command, argument, movie, username, password));
                if (isSent) {
                    Response response = clientResponseReceiver.receiveResponseFromServer();
                    if (response != null) {
                        if (!response.getUserLoggedIn()) {
                            username = null;
                            password = null;
                        }
                        outputManager.printlnMessage(response.getMessage());
                    }
                }
            }
        }
    }
}
package commands;

import application.ClientApplication;
import connect.ClientConnectionManager;
import output.OutputManager;
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
    private ClientConnectionManager clientConnectionManager;
    private ClientRequestSender clientRequestSender;
    private ClientResponseReceiver clientResponseReceiver;
    private MovieBuilder movieBuilder;

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
    public ClientCommandManager(ClientApplication application, OutputManager outputManager, ScriptFilesManager scriptFilesManager, ClientConnectionManager clientConnectionManager,
                                ClientRequestSender clientRequestSender, ClientResponseReceiver clientResponseReceiver, MovieBuilder movieBuilder) {
        this.outputManager = outputManager;
        this.clientConnectionManager = clientConnectionManager;
        this.clientRequestSender = clientRequestSender;
        this.clientResponseReceiver = clientResponseReceiver;
        this.movieBuilder = movieBuilder;

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
                if (command.equals("insert") || command.equals("update"))
                    movie = movieBuilder.buildMovie(0, argument);
                if (clientRequestSender.sendRequestToServer(clientRequestSender.createRequest(command, argument, movie))) {
                    Response response = clientResponseReceiver.receiveResponseFromServer();
                    if (response != null)
                        outputManager.printlnMessage(response.getMessage());
                }
            }
        }
    }
}
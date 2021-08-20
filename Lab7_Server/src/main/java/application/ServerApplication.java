package application;

import collection.CollectionManager;
import commands.SaveCommand;
import commands.ServerCommandManager;
import commands.ServerInvoker;
import connect.ServerConnectionManager;
import database.DBConnector;
import database.DBManager;
import database.DBReader;
import database.DBWriter;
import input.ConsoleInputManager;
import input.InputManager;
import output.ConsoleOutputManager;
import output.OutputManager;
import request.Request;
import response.Response;
import request.ServerRequestReceiver;
import response.ServerResponseSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Класс серверного приложения.
 */
public class ServerApplication implements Application {
    private InputManager inputManager;
    private ServerConnectionManager serverConnectionManager;
    private ServerRequestReceiver serverRequestReceiver;
    private ServerResponseSender serverResponseSender;
    private ServerCommandManager serverCommandManager;
    private OutputManager outputManager;
    private DBWriter dbWriter;
    private boolean shutdown;

    /**
     * Метод, который инициализирует нужные объекты и запускает главный цикл, реализуемый серверным приложением.
     * @param args аргументы командной строки, т.е. имя файла.
     */
    @Override
    public void start (String[] args) {
        shutdown = false;
        this.outputManager = new ConsoleOutputManager();
        this.inputManager = new ConsoleInputManager(new BufferedReader(new InputStreamReader(System.in)));

        DBConnector dbConnector = new DBConnector(outputManager);
        dbConnector.connect();

        DBManager dbManager = new DBManager(dbConnector, outputManager);
        dbManager.createDBEntitiesTableIfNotExists();

        CollectionManager collectionManager = new CollectionManager(dbManager);

        DBReader dbReader = new DBReader(dbConnector, collectionManager, outputManager);
        dbReader.readDBEntitiesTable();

        this.dbWriter = new DBWriter(dbConnector, collectionManager);
        this.serverConnectionManager = new ServerConnectionManager();
        this.serverRequestReceiver = new ServerRequestReceiver(serverConnectionManager);
        this.serverResponseSender = new ServerResponseSender(serverConnectionManager, outputManager);
        this.serverCommandManager = new ServerCommandManager(this, collectionManager, dbWriter, dbConnector);
        loop();
    }

    /**
     * Метод, реализующий главный цикл серверного приложения.
     */
    @Override
    public void loop() {
        outputManager.printlnMessage("Введите серверную команду: ");
        while (!shutdown) {
            serverConnectionManager.acceptConnection();

            if (serverConnectionManager.connectionIsAccepted()) {
                Response response = serverCommandManager.manageRequestOfClient(serverRequestReceiver.receiveRequestFromClient());
                if (response != null)
                    serverResponseSender.sendResponseToClient(response);
            }

            try {
                if (inputManager.readyToRead()) {
                    Request request = new Request(inputManager.readLine().trim().toLowerCase());
                    serverCommandManager.manageRequestOfServer(request);
                }
            } catch (IOException ignored) {
            }
        }

        ServerInvoker invoker = new ServerInvoker(new SaveCommand(dbWriter));
        outputManager.printlnMessage(invoker.executeCommand(new Request("save")).getMessage());
        serverConnectionManager.closeConnection();
        //todo: нужно ли закрывать соединение с БД?
    }

    public void setShutdown() {
        shutdown = true;
    }
}
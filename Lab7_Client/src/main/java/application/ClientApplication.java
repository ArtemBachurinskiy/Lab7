package application;

import commands.ClientCommandManager;
import connect.ClientConnectionManager;
import input.*;
import output.ConsoleOutputManager;
import output.OutputManager;
import builders.ConsoleMovieBuilder;
import builders.ConsolePersonBuilder;
import builders.MovieBuilder;
import builders.PersonBuilder;
import request.ClientRequestSender;
import response.ClientResponseReceiver;
import script.ScriptFilesManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Класс клиентского приложения.
 */
public class ClientApplication implements Application {
    private boolean exit;
    private InputManager inputManager;
    private ClientConnectionManager clientConnectionManager;
    private ClientCommandManager commandManager;

    public ClientApplication() {
        exit = false;
    }

    /**
     * Метод, который инициализирует нужные объекты и запускает
     * главный цикл клиентского приложения.
     */
    @Override
    public void start(String[] args) {
        this.inputManager = new ConsoleInputManager(new BufferedReader(new InputStreamReader(System.in)));
        OutputManager outputManager = new ConsoleOutputManager();
        Validator validator = new Validator(outputManager);
        PersonReader personReader = new ConsolePersonReader(inputManager, outputManager, validator);
        MovieReader movieReader = new ConsoleMovieReader(inputManager, outputManager, validator);
        PersonBuilder personBuilder = new ConsolePersonBuilder(personReader);
        MovieBuilder movieBuilder = new ConsoleMovieBuilder(movieReader, personBuilder);
        ScriptFilesManager scriptFilesManager = new ScriptFilesManager();
        this.clientConnectionManager = new ClientConnectionManager(outputManager);
        ClientRequestSender clientRequestSender = new ClientRequestSender(clientConnectionManager, outputManager);
        ClientResponseReceiver clientResponseReceiver = new ClientResponseReceiver(clientConnectionManager);
        this.commandManager = new ClientCommandManager(this, outputManager, scriptFilesManager, clientConnectionManager, clientRequestSender, clientResponseReceiver, movieBuilder);
        loop();
    }

    /**
     * Метод, который реализует главный клиентский цикл.
     */
    @Override
    public void loop() {
        String fullCommand;
        String command;
        String argument;

        while (!exit) {
            if (!clientConnectionManager.connectionIsEstablished())
                clientConnectionManager.askForConnection();
            try {
                if (inputManager.readyToRead()) {
                    try {
                        argument = "";
                        fullCommand = inputManager.readLine();
                        fullCommand = fullCommand.trim();

                        if (fullCommand.contains(" ")) {
                            command = fullCommand.substring(0, fullCommand.indexOf(" "));
                            argument = fullCommand.substring(fullCommand.indexOf(" ") + 1);
                            argument = argument.trim();
                        } else
                            command = fullCommand;

                        commandManager.manageCommand(command, argument);
                    } catch (IOException ignored) {
                    }
                }
            } catch (IOException ignored) {
            }
        }
        clientConnectionManager.closeConnection();
    }

    /**
     * @return true если мы хотим завершить работу клиентского приложения,
     *         false в противном случае
     */
    public boolean exitIsSet() {
        return exit;
    }

    public void setExit() {
        exit = true;
    }
}

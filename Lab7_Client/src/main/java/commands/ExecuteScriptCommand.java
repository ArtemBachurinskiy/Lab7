package commands;

import application.ClientApplication;
import connect.ClientConnectionManager;
import input.InputManager;
import output.OutputManager;
import input.ScriptInputManager;
import builders.MovieBuilder;
import request.ClientRequestSender;
import response.ClientResponseReceiver;
import script.ScriptFilesManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Класс команды execute_script.
 * Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде,
 * в котором их вводит пользователь в интерактивном режиме.
 */
public class ExecuteScriptCommand implements ClientCommand {
    private ClientApplication application;
    private OutputManager outputManager;
    private ScriptFilesManager scriptFilesManager;
    private ClientConnectionManager clientConnectionManager;
    private ClientRequestSender clientRequestSender;
    private ClientResponseReceiver clientResponseReceiver;
    private MovieBuilder movieBuilder;

    /**
     * @param application объект данного клиентского приложения
     * @param outputManager менеджер вывода данных
     * @param scriptFilesManager менеджер по управлению файлами-скриптами
     * @param clientConnectionManager клиентский менеджер установки соединения
     * @param clientRequestSender клиенткий менеджер отправки запросов
     * @param clientResponseReceiver клиенткий менеджер получения ответов
     * @param movieBuilder объект, предназначенный для создания объекта Movie
     */
    ExecuteScriptCommand(ClientApplication application, OutputManager outputManager, ScriptFilesManager scriptFilesManager,
                         ClientConnectionManager clientConnectionManager, ClientRequestSender clientRequestSender,
                         ClientResponseReceiver clientResponseReceiver, MovieBuilder movieBuilder) {
        this.outputManager = outputManager;
        this.application = application;
        this.scriptFilesManager = scriptFilesManager;
        this.clientConnectionManager = clientConnectionManager;
        this.clientRequestSender = clientRequestSender;
        this.clientResponseReceiver = clientResponseReceiver;
        this.movieBuilder = movieBuilder;
    }

    @Override
    public void execute(String scriptName) throws IOException {
        if (!scriptName.isEmpty()) {
            File scriptFile = new File(scriptName);
            if (scriptFilesManager.getUsedScriptFiles().contains(scriptName)) {
                outputManager.printlnErrorMessage("execute_script не может вызывать ранее использованный скрипт-файл " + '\"' + scriptName + '\"' + "! Пропускаем данную команду execute_script..." + '\n');
            }
            else if (!scriptFile.exists()) {
                outputManager.printlnErrorMessage("Файла скрипта с именем \"" + scriptName + "\" не существует.");
            }
            else if (!scriptFile.canRead()) {
                outputManager.printlnErrorMessage("Отсутствуют права доступа на чтение файла скрипта \"" + scriptName + "\"!");
            }
            else {
                scriptFilesManager.getUsedScriptFiles().add(scriptName);
                InputManager inputManager = new ScriptInputManager(new Scanner(new FileReader(scriptFile)));
                ClientCommandManager commandManager = new ClientCommandManager(application, outputManager, inputManager, scriptFilesManager, clientConnectionManager, clientRequestSender, clientResponseReceiver, movieBuilder);
                String fullCommand;
                String command;
                String argument;
                while (inputManager.readyToRead() && !application.exitIsSet()) {
                    argument = "";
                    fullCommand = inputManager.readLine();
                    fullCommand = fullCommand.trim();

                    if (fullCommand.contains(" ")) {
                        command = fullCommand.substring(0, fullCommand.indexOf(" "));
                        argument = fullCommand.substring(fullCommand.indexOf(" ") + 1);
                        argument = argument.trim();
                    } else
                        command = fullCommand;

                    if (!command.equals("")) {
                        if (!argument.equals(""))
                            outputManager.printlnMessage(command + " " + argument + " (" + scriptName + ")");
                        else
                            outputManager.printlnMessage(command + " (" + scriptName + ")");
                    }
                    commandManager.manageCommand(command, argument);
                    if (!command.equals("") && !command.equals("execute_script") && inputManager.readyToRead()) { //если исполнялась какая-то команда и она была не execute_script
                        outputManager.printlnMessage("");
                    }
                }
                outputManager.printlnMessage("");
                scriptFilesManager.getUsedScriptFiles().remove(scriptName);
            }
        }
        else
            outputManager.printlnErrorMessage("Необходимо задать имя файла скрипта!");
    }
}

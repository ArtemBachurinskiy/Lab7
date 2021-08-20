package commands;

import application.ServerApplication;
import request.Request;
import response.Response;

/**
 * Класс серверной команды shutdown.
 * Предназначена для завершения работы серверного приложения.
 */
public class ShutdownCommand implements ServerCommand {
    private ServerApplication application;

    ShutdownCommand(ServerApplication application) {
        this.application = application;
    }

    @Override
    public Response execute(Request request) {
        application.setShutdown();
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }
}

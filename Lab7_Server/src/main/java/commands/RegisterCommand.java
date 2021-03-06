package commands;

import database.DBWriter;
import request.Request;
import response.Response;

/**
 * Класс команды register.
 * Описание команды: зарегистрировать нового пользователя
 */
public class RegisterCommand implements ServerCommand{
    private DBWriter dbWriter;

    public RegisterCommand(DBWriter dbWriter) {
        this.dbWriter = dbWriter;
    }

    @Override
    public Response execute(Request request) {
        String message = dbWriter.registerNewUser(request.getUsername(), request.getPassword());
        return new Response(request.getCommand(), message, true);
    }

    @Override
    public String getDescription() {
        return "register : зарегистрировать нового пользователя";
    }
}

package commands;

import database.DBReader;
import request.Request;
import response.Response;


/**
 * Класс команды login.
 * Описание команды: войти в систему
 */
public class LoginCommand implements ServerCommand{
    private DBReader dbReader;

    public LoginCommand(DBReader dbReader) {
        this.dbReader = dbReader;
    }

    @Override
    public Response execute(Request request) {
        return new Response(null, dbReader.getValidateUserOK(), true);
    }

    @Override
    public String getDescription() { return "login : войти в систему";
    }
}

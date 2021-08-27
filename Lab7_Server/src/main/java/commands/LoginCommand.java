package commands;

import database.DBReader;
import request.Request;
import response.Response;

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
    public String getDescription() {
        return null;
    }
}

package commands;

import request.Request;
import response.Response;

public class ServerInvoker {
    private ServerCommand command;

    public ServerInvoker(ServerCommand command) {
        this.command = command;
    }

    public Response executeCommand(Request request) {
        return command.execute(request);
    }
}

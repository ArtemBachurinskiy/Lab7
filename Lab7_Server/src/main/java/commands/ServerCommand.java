package commands;

import request.Request;
import response.Response;

public interface ServerCommand {
    Response execute(Request request);
    String getDescription();
}

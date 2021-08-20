package response;

import connect.ServerConnectionManager;
import output.OutputManager;
import response.Response;

import java.io.IOException;

public class ServerResponseSender {
    private ServerConnectionManager serverConnectionManager;
    private OutputManager outputManager;

    public ServerResponseSender(ServerConnectionManager serverConnectionManager, OutputManager outputManager) {
        this.serverConnectionManager = serverConnectionManager;
        this.outputManager = outputManager;
    }

    public void sendResponseToClient(Response response) {
        try {
            serverConnectionManager.getObjectOutputStream().writeObject(response);
        } catch (IOException e) {
            outputManager.printlnErrorMessage("Не удалось отправить ответ клиенту ...");
        }
    }
}
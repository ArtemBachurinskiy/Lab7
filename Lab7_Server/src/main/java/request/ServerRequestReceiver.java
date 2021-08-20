package request;

import connect.ServerConnectionManager;
import request.Request;

import java.io.IOException;

public class ServerRequestReceiver {
    private ServerConnectionManager serverConnectionManager;

    public ServerRequestReceiver(ServerConnectionManager serverConnectionManager) {
        this.serverConnectionManager = serverConnectionManager;
    }

    public Request receiveRequestFromClient() {
        try {
            return (Request) serverConnectionManager.getObjectInputStream().readObject();
        } catch (IOException | ClassNotFoundException e) {
            serverConnectionManager.closeConnection();
            return null;
        }
    }
}
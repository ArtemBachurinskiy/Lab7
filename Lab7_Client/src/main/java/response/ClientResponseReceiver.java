package response;

import connect.ClientConnectionManager;
import response.Response;

import java.io.IOException;

/**
 * Клиентский класс, предназначенный для получения ответа от сервера.
 */
public class ClientResponseReceiver {
    private ClientConnectionManager clientConnectionManager;

    public ClientResponseReceiver(ClientConnectionManager clientConnectionManager) {
        this.clientConnectionManager = clientConnectionManager;
    }

    /**
     * Метод, который получает ответ от сервера.
     * @return ответ сервера (объект типа Response) или null, если произошло какое-либо иключение.
     */
    public Response receiveResponseFromServer() {
        try {
            return (Response) clientConnectionManager.getObjectInputStream().readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
}
}

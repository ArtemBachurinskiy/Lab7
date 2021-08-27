package request;

import connect.ClientConnectionManager;
import output.OutputManager;
import request.Request;
import entities.Movie;

import java.io.IOException;

/**
 * Клиентский класс, предназначенный для отпраки запросов серверу.
 */
public class ClientRequestSender {
    private ClientConnectionManager clientConnectionManager;
    private OutputManager outputManager;

    /**
     * Конструктор ClientRequestSender.
     * @param clientConnectionManager клиентский менеджер установки соединения
     * @param outputManager менеджер вывода данных
     */
    public ClientRequestSender(ClientConnectionManager clientConnectionManager, OutputManager outputManager) {
        this.clientConnectionManager = clientConnectionManager;
        this.outputManager = outputManager;
    }

    /**
     * Метод, который посылает запрос серверу.
     * @param request посылаемый запрос
     * @return true если запрос успешно отправлен,
     *         false если не удалось отправить запрос.
     */
    public boolean sendRequestToServer(Request request) {
        try {
            clientConnectionManager.getObjectOutputStream().writeObject(request);
            return true;
        } catch (IOException e) {
            outputManager.printlnErrorMessage("Не удалось послать запрос серверу ...");
            clientConnectionManager.closeConnection();
            return false;
        }
    }
}

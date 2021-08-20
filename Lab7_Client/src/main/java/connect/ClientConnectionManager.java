package connect;

import output.OutputManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Класс клиентского менеджера по установке соединения с сервером.
 */
public class ClientConnectionManager {
    private Socket clientSocket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private OutputManager outputManager;
    private boolean messageIsPrinted = false;

    /**
     * @param outputManager менеджер вывода данных
     */
    public ClientConnectionManager(OutputManager outputManager) {
        this.outputManager = outputManager;
        clientSocket = new Socket();
    }

    /**
     * Метод, предназначенный для установки соединения с сервером.
     */
    public void askForConnection() {
        try {
            clientSocket = new Socket(InetAddress.getLocalHost(), 13579);
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            outputManager.printlnMessage("Соединение с сервером установлено.");
        } catch (IOException e) {
            if (!messageIsPrinted) {
                outputManager.printlnErrorMessage("Не удалось установить соединение с сервером ...");
                messageIsPrinted = true;
            }
        }
    }

    /**
     * Метод, который проверяет, установлено ли соединения с сервером.
     * @return true если соединение установлено,
     *         false в противном случае.
     */
    public boolean connectionIsEstablished () {
        return clientSocket.isConnected() && (!clientSocket.isClosed());
    }

    /**
     * Метод, который закрывает соединение с сервером с клиентской стороны.
     */
    public void closeConnection () {
        try {
            clientSocket.shutdownOutput();
            clientSocket.shutdownInput();
            clientSocket.close();
        } catch (IOException e) {
            outputManager.printlnErrorMessage("Не удалось закрыть соединение ...");
        }
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }
}

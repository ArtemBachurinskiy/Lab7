package connect;

import output.OutputManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerConnectionManager {
    private OutputManager outputManager;
    private ServerSocketChannel serverSocketChannel;
    private SocketChannel communicationSocketChannel;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private boolean needNewStreams;

    public ServerConnectionManager(OutputManager outputManager, String port) {
        this.outputManager = outputManager;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(Integer.parseInt(port)));
            serverSocketChannel.configureBlocking(false);
            communicationSocketChannel = null;
            needNewStreams = true;
        } catch (NumberFormatException e) {
            outputManager.printlnErrorMessage("Указанный порт для подключения к клиенту не преобразуется к типу int.");
            System.exit(0);
        } catch (IOException e) {
        }
    }

    public void acceptConnection() {
        try {
            if (!connectionIsAccepted())
                communicationSocketChannel = serverSocketChannel.accept();
            if (communicationSocketChannel == null) {
                needNewStreams = true;
            }
            if (communicationSocketChannel != null && needNewStreams) {
                objectInputStream = new ObjectInputStream(Channels.newInputStream(communicationSocketChannel));
                objectOutputStream = new ObjectOutputStream(Channels.newOutputStream(communicationSocketChannel));
                needNewStreams = false;
            }
        } catch (IOException e) {
        }
    }

    public void closeConnection() {
        try {
            if (communicationSocketChannel != null) {
                communicationSocketChannel.shutdownInput();
                communicationSocketChannel.shutdownOutput();
                objectInputStream.close();
                objectOutputStream.close();
                communicationSocketChannel.close();
                communicationSocketChannel = null;
            }
        } catch (IOException ignored) {
        }
    }

    public boolean connectionIsAccepted() {
        return communicationSocketChannel != null;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }
}
package main;

import application.ClientApplication;
import application.Application;

/**
 * Класс Main клиентского приложения.
 * @author Artem Bachurinskiy
 **/
public class ClientMain {
    public static void main(String[] args) {
        Application application = new ClientApplication();
        application.start(args);
    }
}
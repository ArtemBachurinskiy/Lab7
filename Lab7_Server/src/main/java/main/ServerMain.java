package main;

import application.ServerApplication;
import application.Application;

public class ServerMain {
    public static void main(String[] args) {
        Application application = new ServerApplication();
        application.start(args);
    }
}

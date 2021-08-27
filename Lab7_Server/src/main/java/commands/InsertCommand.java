package commands;

import collection.CollectionManager;
import database.DBReader;
import database.DBWriter;
import entities.Movie;
import request.Request;
import response.Response;

/**
 * Класс команды insert.
 * Описание команды: добавить новый элемент с заданным ключом
 */
public class InsertCommand implements ServerCommand {
    private CollectionManager collectionManager;
    private DBWriter dbWriter;
    private DBReader dbReader;

    InsertCommand (CollectionManager collectionManager, DBWriter dbWriter, DBReader dbReader) {
        this.collectionManager = collectionManager;
        this.dbWriter = dbWriter;
        this.dbReader = dbReader;
    }

    @Override
    public Response execute(Request request) {
        String message;
        if (!request.getArgument().isEmpty()) {
            boolean inserted = dbWriter.insertEntityIntoDB(request.getMovie(), request.getUsername());
            if (inserted) {
                message = "Объект успешно добавлен в БД! БД и коллекция синхронизированы.";
                Movie movie = dbReader.getMovieWithMaxId();
                if (movie != null)
                    collectionManager.insertMovie(request.getArgument(), movie);
            }
            else
                message = "Не удалось добавить объект в БД...";
        }
        else
            message = "Необходимо задать аргумент!";
        return new Response(request.getCommand(), message, true);
    }

    public String getDescription() {
        return "insert null {element} : добавить новый элемент с заданным ключом";
    }
}
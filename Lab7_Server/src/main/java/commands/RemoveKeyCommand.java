package commands;

import collection.CollectionManager;
import database.DBReader;
import database.DBWriter;
import entities.Movie;
import request.Request;
import response.Response;

/**
 * Класс команды remove_key.
 * Описание команды: удалить элемент из коллекции по его ключу
 */
public class RemoveKeyCommand implements ServerCommand {
    private CollectionManager collectionManager;
    private DBWriter dbWriter;
    private DBReader dbReader;

    RemoveKeyCommand(CollectionManager collectionManager, DBWriter dbWriter, DBReader dbReader) {
        this.collectionManager = collectionManager;
        this.dbWriter = dbWriter;
        this.dbReader = dbReader;
    }

    @Override
    public Response execute(Request request) {
        String message;
        if (!request.getArgument().isEmpty()) {
            Movie movie = collectionManager.getMovieByKey(request.getArgument());
            if (movie != null) {
                if (dbReader.userIsDenied(movie.getId(), request.getUsername()))
                    return new Response(request.getCommand(), "Пользователь '" + request.getUsername() + "' не может модифицировать запись, созданную другим пользователем.", true);
                boolean deleted = dbWriter.deleteEntityFromDB(movie.getId());
                if (deleted) {
                    collectionManager.remove_key(request.getArgument());
                    message = "Объект успешно удалён из БД! БД и коллекция синхронизированы.";
                }
                else
                    message = "Не удалось удалить объект...";
            }
            else
                message = "В коллекции нет элемента с заданным ключом!";
        }
        else
            message = "Необходимо задать аргумент!";

        return new Response(request.getCommand(), message, true);
    }

    public String getDescription() {
        return "remove_key null : удалить элемент из коллекции по его ключу";
    }
}

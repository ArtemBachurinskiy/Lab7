package commands;

import collection.CollectionManager;
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

    /**
     * @param collectionManager менеджер коллекции
     */
    RemoveKeyCommand(CollectionManager collectionManager, DBWriter dbWriter) {
        this.collectionManager = collectionManager;
        this.dbWriter = dbWriter;
    }

    @Override
    public Response execute(Request request) {
        String message;
        if (!request.getArgument().isEmpty()) {
            Movie movie = collectionManager.getMovieByKey(request.getArgument());
            if (movie != null) {
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

        return new Response(request.getCommand(), message);
    }

    public String getDescription() {
        return "remove_key null : удалить элемент из коллекции по его ключу";
    }
}

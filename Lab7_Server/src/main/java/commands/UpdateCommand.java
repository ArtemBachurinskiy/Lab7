package commands;

import database.DBReader;
import database.DBWriter;
import entities.Movie;
import collection.CollectionManager;
import request.Request;
import response.Response;

import java.util.ArrayList;
import java.util.Map;

/**
 * Класс команды update.
 * Описание команды: обновить значение элемента коллекции, id которого равен заданному
 */
public class UpdateCommand implements ServerCommand {
    private CollectionManager collectionManager;
    private DBWriter dbWriter;

    UpdateCommand(CollectionManager collectionManager, DBWriter dbWriter, DBReader dbReader) {
        this.collectionManager = collectionManager;
        this.dbWriter = dbWriter;
    }

    //todo: lambda
    // Выдаёт ConcurrentModificationException...
    // collectionManager.getMoviesStream()
    //                  .filter(movie -> movie.getId().equals(Integer.parseInt(request.getArgument())))
    //                  .forEach(movie -> collectionManager.insertMovie(new Request(request.getCommand(), request.getArgument(), request.getMovie())));

    @Override
    public Response execute(Request request) {
        StringBuilder message = new StringBuilder();
        int id = Integer.parseInt(request.getArgument());

        if (!request.getArgument().isEmpty()) {
            boolean found = false;
            ArrayList<Map.Entry<String, Movie>> elements = collectionManager.getCollectionElements();
            for (Map.Entry<String, Movie> element : elements)
                if (element.getValue().getId().equals(id)) {
                    found = true;
                    message.append("Обновляем поля элемента БД...\n");
                    boolean updated = dbWriter.updateEntityOfDB(id, request.getMovie());
                    if (updated) {
                        message.append("Объект БД успешно обновлён!");
                        collectionManager.deleteMovieById(id);
                        collectionManager.insertMovie(request.getMovie().getName(), request.getMovie());
                    }
                    else
                        message.append("Не удалось обновить объект БД...");
                }
            if (!found)
                message.append("Элемента с id = ").append(request.getArgument()).append(" нет.");
        }
        else
            message.append("Необходимо задать аргумент!");


        return new Response(request.getCommand(), message.toString());
    }

    public String getDescription() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }
}

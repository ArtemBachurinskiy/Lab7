package commands;

import entities.Movie;
import collection.CollectionManager;
import request.Request;
import response.Response;

import java.util.ArrayList;
import java.util.Map;

/**
 * Класс команды update.
 * Описание комнады: обновить значение элемента коллекции, id которого равен заданному
 */
public class UpdateCommand implements ServerCommand {
    private CollectionManager collectionManager;

    /**
     * @param collectionManager менеджер коллекции
     */
    UpdateCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    //todo: lambda
    @Override
    public Response execute(Request request) {
        String message = "";

        if (!request.getArgument().isEmpty()) {
            boolean found = false;
            ArrayList<Map.Entry<String, Movie>> elements = collectionManager.getCollectionElements();
            for (Map.Entry<String, Movie> element : elements)
                if (element.getValue().getId().equals(Integer.parseInt(request.getArgument()))) {
                    found = true;
                    message = "Обновляем поля элемента коллекции...";
                    collectionManager.insertMovie(request);
                }
            if (!found)
                message = "Элемента коллекции с id = " + request.getArgument() + " нет.";
        }
        else
            message = "Необходимо задать аргумент!";

//        Выдаёт ConcurrentModificationException...
//        collectionManager.getMoviesStream()
//                    .filter(movie -> movie.getId().equals(Integer.parseInt(request.getArgument())))
//                    .forEach(movie -> collectionManager.insertMovie(new Request(request.getCommand(), request.getArgument(), request.getMovie())));

        return new Response(request.getCommand(), message);
    }

    public String getDescription() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }
}

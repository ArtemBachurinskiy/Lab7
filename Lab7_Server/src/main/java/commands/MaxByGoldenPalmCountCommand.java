package commands;

import entities.Movie;
import collection.CollectionManager;
import request.Request;
import response.Response;

import java.util.Comparator;

/**
 * Класс команды max_by_golden_palm_count.
 * Описание команды: вывести любой объект из коллекции, значение поля goldenPalmCount которого является максимальным
 */
public class MaxByGoldenPalmCountCommand implements ServerCommand {
    private CollectionManager collectionManager;

    MaxByGoldenPalmCountCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        String message;
        if (collectionManager.getCollectionSize() > 0) {
            message = collectionManager.getMoviesStream()
                        .max(Comparator.comparingLong(Movie::getGoldenPalmCount))
                        .toString();
        }
        else
            message = "В коллекции нет элементов!";
        return new Response(request.getCommand(), message, true);
    }

    public String getDescription() {
        return "max_by_golden_palm_count : вывести любой объект из коллекции, значение поля goldenPalmCount которого является максимальным";
    }
}
